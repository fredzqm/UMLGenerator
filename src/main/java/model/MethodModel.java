package model;

import analyzer.IVisitable;
import analyzer.IVisitor;
import generator.IFieldModel;
import generator.IMethodModel;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import utility.MethodType;
import utility.Modifier;

import java.util.ArrayList;
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

	private List<MethodModel> superMethods;
	private Collection<MethodModel> dependenOnMethod;
	private Collection<IFieldModel> dependenOnField;

	public MethodModel(ClassModel belongsTo, MethodNode methodNode) {
		this.belongsTo = belongsTo;
		this.asmMethodNode = methodNode;
		this.modifier = Modifier.parse(methodNode.access);
		this.isFinal = Modifier.parseIsFinal(asmMethodNode.access);
		this.methodtype = MethodType.parse(asmMethodNode.name, asmMethodNode.access);
		this.returnType = TypeModel.parse(belongsTo, Type.getReturnType(methodNode.desc));
		this.signature = Signature.parse(belongsTo, methodNode.name, methodNode.desc);
	}

	public ClassModel getParentClass() {
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

	public Iterable<MethodModel> getSuperMethods() {
		if (superMethods == null) {
			superMethods = new ArrayList<>();
			MethodModel inherit = belongsTo.getMethodBySignature(signature);
			if (inherit != null)
				superMethods.add(inherit);
			for (ClassModel interf : belongsTo.getInterfaces()) {
				inherit = interf.getMethodBySignature(signature);
				if (inherit != null)
					superMethods.add(inherit);
			}
		}
		return superMethods;
	}

	public List<TypeModel> getArguments() {
		return signature.getArguments();
	}

	public TypeModel getReturnType() {
		return returnType;
	}

	@Override
	public String toString() {
		return "" + returnType + " " + getSignature().toString();
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
					ClassModel destClass = belongsTo.getClassByName(methodCall.owner);
					Signature signature = Signature.parse(belongsTo, methodCall.name, methodCall.desc);
					MethodModel method = destClass.getMethodBySignature(signature);
					dependenOnMethod.add(method);
				}
			}
		}
		return dependenOnMethod;
	}

	public Collection<IFieldModel> getDependentFields() {
		if (dependenOnField == null) {
			dependenOnField = new ArrayList<>();
			InsnList instructions = asmMethodNode.instructions;
			for (int i = 0; i < instructions.size(); i++) {
				AbstractInsnNode insn = instructions.get(i);
				// if (insn instanceof FieldInsnNode) {
				// MethodInsnNode methodCall = (MethodInsnNode) insn;
				// ClassModel destClass =
				// belongsTo.getClassByName(methodCall.owner);
				// FieldModel method = destClass
				// .getFieldByName(Signature.parse(belongsTo, methodCall.name,
				// methodCall.desc));
				// dependenOnField.add(method);
				// }
			}
		}
		return dependenOnField;
	}

}
