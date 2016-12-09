package main.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class ClassModel implements Visitable<ClassModel>, ASMServiceProvider {
	private final ASMServiceProvider asmServiceProvider;
	private final ClassNode asmClassNode;

	private final boolean important;
	private final ClassType classType;
	private final Modifier modifier;
	private final boolean isFinal;
	private final String name;

	private ClassModel superClass;
	private Collection<ClassModel> interfaces;

	private Collection<FieldModel> fileds;

	private Map<Signature, MethodModel> constructors;
	private Map<Signature, MethodModel> methods;
	private Map<Signature, MethodModel> staticMethods;
	private MethodModel staticConstructor;

	/**
	 * Creates an ClassModel and assign its basic properties.
	 * 
	 * @param asmServiceProvider
	 * @param asmClassNode
	 * @param important
	 */
	public ClassModel(ASMServiceProvider asmServiceProvider, ClassNode asmClassNode, boolean important) {
		this.asmServiceProvider = asmServiceProvider;
		this.asmClassNode = asmClassNode;
		this.important = important;
		this.classType = ClassType.parse(asmClassNode.access);
		this.modifier = Modifier.parse(asmClassNode.access);
		this.isFinal = (asmClassNode.access & Opcodes.ACC_FINAL) != 0;
		this.name = Type.getObjectType(asmClassNode.name).getClassName();
	}

	protected void tranceInheritanceChain() {
		if (asmClassNode.superName != null)
			superClass = getClassByName(asmClassNode.superName);
	}

	public ClassModel getSuperClass() {
		return superClass;
	}

	public boolean isImportant() {
		return important;
	}

	public String getName() {
		return name;
	}

	public ClassType getType() {
		return classType;
	}

	public Modifier getModifier() {
		return modifier;
	}

	public Iterable<ClassModel> getInterfaces() {
		if (interfaces == null) {
			interfaces = new ArrayList<ClassModel>();
			@SuppressWarnings("unchecked")
			List<String> ls = asmClassNode.interfaces;
			for (String s : ls) {
				interfaces.add(getClassByName(s));
			}
		}
		return interfaces;
	}

	public Iterable<MethodModel> getMethods() {
		return getMethodsMap().values();
	}

	public MethodModel getMethodBySignature(Signature signature) {
		if (methods.containsKey(signature))
			return methods.get(signature);
		return null;
	}

	public Iterable<MethodModel> getConstructors() {
		return getConstructorMap().values();
	}

	public Iterable<MethodModel> getStaticMethods() {
		return getStaticMethodMap().values();
	}

	public MethodModel getStaticInitializer() {
		lazyInitializeMethods();
		return staticConstructor;
	}

	private Map<Signature, MethodModel> getMethodsMap() {
		lazyInitializeMethods();
		return methods;
	}

	private Map<Signature, MethodModel> getConstructorMap() {
		lazyInitializeMethods();
		return constructors;
	}

	private Map<Signature, MethodModel> getStaticMethodMap() {
		lazyInitializeMethods();
		return staticMethods;
	}

	private void lazyInitializeMethods() {
		if (methods == null) {
			constructors = new HashMap<>();
			staticMethods = new HashMap<>();
			if (superClass == null)
				methods = new HashMap<>();
			else
				methods = new HashMap<>(superClass.getMethodsMap());

			@SuppressWarnings("unchecked")
			List<MethodNode> ls = asmClassNode.methods;
			for (MethodNode methodNode : ls) {
				MethodModel methodModel = new MethodModel(this, methodNode);
				Signature signature = methodModel.getSignature();
				switch (methodModel.getMethodType()) {
				case METHOD:
				case ABSTRACT:
					methods.put(signature, methodModel);
					break;
				case CONSTRUCTOR:
					constructors.put(signature, methodModel);
					break;
				case STATIC:
					staticMethods.put(signature, methodModel);
					break;
				case STATIC_INITIALIZER:
					staticConstructor = methodModel;
					break;
				}
			}
		}
	}

	public Iterable<FieldModel> getFields() {
		if (fileds == null) {

		}
		return fileds;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public enum ClassType {
		ABSTRACT, INTERFACE, CONCRETE, ENUM;

		public static ClassType parse(int access) {
			if ((access & Opcodes.ACC_ENUM) != 0)
				return ClassType.ENUM;
			if ((access & Opcodes.ACC_INTERFACE) != 0)
				return ClassType.INTERFACE;
			if ((access & Opcodes.ACC_ABSTRACT) != 0)
				return ClassType.ABSTRACT;
			return CONCRETE;
		}
	}

	public ClassModel getClassByName(String name) {
		return asmServiceProvider.getClassByName(name);
	}

	@Override
	public void visit(Visitor<ClassModel> visitor) {
		visitor.visit(this);
	}

}
