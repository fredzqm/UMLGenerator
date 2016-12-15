package model;

import analyzer.IVisitable;
import analyzer.IVisitor;
import generator.IClassModel;
import generator.IMethodModel;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.*;

/**
 * Representing classes in java program
 * 
 * This class handles inheritance, dependency relationship among classes. 
 * 
 * 
 * A class may have four different type of methods -- constructor, instance
 * method, static method, static initializer This model has four separate
 * getters for each of them
 * 
 * @author zhang
 *
 */
public class ClassModel implements IVisitable<ClassModel>, ASMServiceProvider, IClassModel {
	private final ASMServiceProvider asmServiceProvider;
	private final ClassNode asmClassNode;

	private final Modifier modifier;
	private final boolean isFinal;
	private final ClassType classType;
	private final String name;

	private ClassModel superClass;
	private Collection<ClassModel> interfaces;

	private Map<String, FieldModel> fields;
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
	public ClassModel(ASMServiceProvider asmServiceProvider, ClassNode asmClassNode) {
		this.asmServiceProvider = asmServiceProvider;
		this.asmClassNode = asmClassNode;
		this.modifier = Modifier.parse(asmClassNode.access);
		this.isFinal = Modifier.parseIsFinal(asmClassNode.access);
		this.classType = ClassType.parse(asmClassNode.access);
		this.name = Type.getObjectType(asmClassNode.name).getClassName();
	}

	public IClassModel getSuperClass() {
		if (superClass == null && asmClassNode.superName != null)
			superClass = getClassByName(asmClassNode.superName);
		return superClass;
	}

	public String getName() {
		return name;
	}

	public IClassType getType() {
		return classType;
	}

	public Modifier getModifier() {
		return modifier;
	}

	public Iterable<ClassModel> getInterfaces() {
		if (interfaces == null) {
			interfaces = new ArrayList<>();
			@SuppressWarnings("unchecked")
			List<String> ls = asmClassNode.interfaces;
			for (String s : ls) {
				ClassModel m = getClassByName(s);
				if (m != null)
					interfaces.add(m);
			}
		}
		return interfaces;
	}

	@Override
	public List<IClassModel> getHasRelation() {
		return new ArrayList<>();
	}

	@Override
	public List<IClassModel> getDependsRelation() {
		return new ArrayList<>();
	}

	@Override
	public String getSuperClassName() {
		return this.superClass.getName();
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

	public IMethodModel getStaticInitializer() {
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
			if (getSuperClass() == null)
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
		return getFieldMap().values();
	}

	private Map<String, FieldModel> getFieldMap() {
		if (fields == null) {
			fields = new HashMap<>();
			@SuppressWarnings("unchecked")
			List<FieldNode> ls = asmClassNode.fields;
			for (FieldNode fieldNode : ls) {
				FieldModel fieldModel = new FieldModel(this, fieldNode);
				fields.put(fieldModel.getName(), fieldModel);
			}
		}
		return fields;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public ClassModel getClassByName(String name) {
		return asmServiceProvider.getClassByName(name);
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public void visit(IVisitor<ClassModel> IVisitor) {
		IVisitor.visit(this);
	}

	public enum ClassType implements IClassType {
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

		@Override
		public void switchByCase(Switcher switcher) {
			switch (this) {
			case ABSTRACT:
				switcher.ifAbstract();
				break;
			case INTERFACE:
				switcher.ifInterface();
				break;
			case CONCRETE:
				switcher.ifConcrete();
				break;
			case ENUM:
				switcher.ifEnum();
				break;
			}
		}
	}

}
