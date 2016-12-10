package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodNode;

/**
 * The method model.
 * 
 * @author zhang
 *
 */
public class MethodModel implements Visitable<MethodModel> {
	private final MethodNode asmMethodNode;
	private final ClassModel belongsTo;

	private final Modifier modifier;
	private final boolean isFinal;
	private final MethodType methodtype;
	private final Signature signature;

	private Collection<MethodModel> superMethods;

	public MethodModel(ClassModel belongsTo, MethodNode methodNode) {
		this.belongsTo = belongsTo;
		this.asmMethodNode = methodNode;
		this.modifier = Modifier.parse(methodNode.access);
		this.isFinal = Modifier.parseIsFinal(asmMethodNode.access);
		this.methodtype = MethodType.parse(asmMethodNode.name, asmMethodNode.access);
		this.signature = Signature.parse(belongsTo, parseMethodName(), asmMethodNode.desc);
	}

	private String parseMethodName() {
		switch (methodtype) {
		case CONSTRUCTOR:
			String name = belongsTo.getName();
			return name.substring(name.indexOf('.') + 1);
		case STATIC_INITIALIZER:
			return "static";
		default:
			return asmMethodNode.name;
		}
	}

	public ClassModel getParentClass() {
		return belongsTo;
	}

	public String getName() {
		return signature.getName();
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
		return signature.getArgumentList();
	}

	public TypeModel getReturnType() {
		return signature.getReturnType();
	}

	public Collection<MethodModel> getDependMethods() {
		// TODO:
		return null;
	}

	public Collection<FieldModel> getDependFields() {
		// TODO:
		return null;
	}

	public enum MethodType {
		CONSTRUCTOR, STATIC_INITIALIZER, METHOD, STATIC, ABSTRACT;

		public static MethodType parse(String name, int access) {
			if ((access & Opcodes.ACC_STATIC) != 0) {
				if (name.equals("<clinit>"))
					return STATIC_INITIALIZER;
				return STATIC;
			} else {
				if ((access & Opcodes.ACC_ABSTRACT) != 0)
					return ABSTRACT;
				if (name.equals("<init>"))
					return CONSTRUCTOR;
				return METHOD;
			}
		}
	}

	@Override
	public void visit(Visitor<MethodModel> visitor) {
		visitor.visit(this);
	}

}
