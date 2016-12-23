package model;

import analyzer.IVisitable;
import analyzer.IVisitor;
import generator.IMethodModel;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import utility.MethodType;
import utility.Modifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Representing method in java program
 *
 * @author zhang
 */
class MethodModel implements IVisitable<MethodModel>, IMethodModel {
	private final MethodNode asmMethodNode;
	private final ClassModel belongsTo;

	private final Modifier modifier;
	private final boolean isFinal;
	private final MethodType methodtype;

	private final TypeModel returnType;
	private final Signature signature;

	private Collection<MethodModel> dependenOnMethod;
	private Collection<FieldModel> dependenOnField;
	private Collection<ClassModel> dependsOn;

	/**
	 * constructs an method model given the class it belongs to and the asm
	 * method node
	 * 
	 * @param belongsTo
	 * @param methodNode
	 */
	public MethodModel(ClassModel belongsTo, MethodNode methodNode) {
		this.belongsTo = belongsTo;
		this.asmMethodNode = methodNode;
		this.modifier = Modifier.parse(methodNode.access);
		this.isFinal = Modifier.parseIsFinal(asmMethodNode.access);
		this.methodtype = MethodType.parse(asmMethodNode.name, asmMethodNode.access);
		this.returnType = TypeModel.parse(Type.getReturnType(methodNode.desc));
		this.signature = Signature.parse(methodNode.name, methodNode.desc);
	}

	public ClassModel getBelongTo() {
		return belongsTo;
	}

	public String getName() {
		switch (methodtype) {
		case CONSTRUCTOR:
			return belongsTo.getName();
		case STATIC_INITIALIZER:
			return "static";
		default:
			return signature.getName();
		}
	}

	public MethodType getMethodType() {
		return methodtype;
	}

	public Modifier getModifier() {
		return modifier;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public Signature getSignature() {
		return signature;
	}

	public List<TypeModel> getArguments() {
		return signature.getArguments();
	}

	public TypeModel getReturnType() {
		return returnType;
	}

	@Override
	public String toString() {
		return returnType + " " + getSignature().toString();
	}

	@Override
	public void visit(IVisitor<MethodModel> IVisitor) {
		IVisitor.visit(this);
	}

	public Collection<MethodModel> getDependentMethods() {
		if (dependenOnMethod == null) {
			dependenOnMethod = new HashSet<>();
			InsnList instructions = asmMethodNode.instructions;
			for (int i = 0; i < instructions.size(); i++) {
				AbstractInsnNode insn = instructions.get(i);
				if (insn instanceof MethodInsnNode) {
					MethodInsnNode methodCall = (MethodInsnNode) insn;
					TypeModel type = TypeModel.parse(Type.getObjectType(methodCall.owner));
					ClassModel destClass = ASMParser.getClassByName(type.getName());
					if (destClass == null)
						continue;
					Signature signature = Signature.parse(methodCall.name, methodCall.desc);
					MethodModel method = destClass.getMethodBySignature(signature);
					if (method == null)
						continue;
					dependenOnMethod.add(method);
				}
			}
		}
		return dependenOnMethod;
	}

	public Collection<FieldModel> getDependentFields() {
		if (dependenOnField == null) {
			dependenOnField = new HashSet<>();
			InsnList instructions = asmMethodNode.instructions;
			for (int i = 0; i < instructions.size(); i++) {
				AbstractInsnNode insn = instructions.get(i);
				if (insn instanceof FieldInsnNode) {
					FieldInsnNode fiedlCall = (FieldInsnNode) insn;
					TypeModel type = TypeModel.parse(Type.getObjectType(fiedlCall.owner));
					ClassModel destClass = ASMParser.getClassByName(type.getName());
					if (destClass == null)
						continue;
					FieldModel field = destClass.getFieldByName(fiedlCall.name);
					if (field == null)
						throw new RuntimeException(
								"The destination class " + destClass + " does not contain a field: " + fiedlCall.name);
					dependenOnField.add(field);
				}
			}
		}
		return dependenOnField;
	}

	public Collection<ClassModel> addDependsClasses() {
		if (dependsOn == null) {
			dependsOn = new HashSet<>();
			for (TypeModel arg : getArguments()) {
				ClassModel asArgument = arg.getClassModel();
				if (asArgument != null)
					dependsOn.add(asArgument);
			}
			ClassModel asReturnType = getReturnType().getClassModel();
			if (asReturnType != null)
				dependsOn.add(asReturnType);
			for (FieldModel field : getDependentFields()) {
				dependsOn.add(field.getBelongTo());
			}
			for (MethodModel method : getDependentMethods()) {
				dependsOn.add(method.getBelongTo());
			}
		}
		return dependsOn;
	}

}
