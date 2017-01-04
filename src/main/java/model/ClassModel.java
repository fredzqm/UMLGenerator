package model;

import analyzer.IVisitable;
import analyzer.IVisitor;
import generator.classParser.IClassModel;
import model.type.GenericTypeModel;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import utility.ClassType;
import utility.Modifier;

import java.util.*;

/**
 * Representing classes in java program
 * <p>
 * This class handles inheritance, dependency relationship among classes.
 * <p>
 * <p>
 * A class may have four different type of methods -- constructor, instance
 * method, static method, static initializer This model has four separate
 * getters for each of them
 *
 * @author zhang
 */
public class ClassModel implements IVisitable<ClassModel>, IClassModel {
	private final ClassNode asmClassNode;

	private final Modifier modifier;
	private final boolean isFinal;
	private final ClassType classType;
	private final String name;

	private ClassModel superClass;
	private Collection<ClassModel> interfaces;
	private List<GenericTypeModel> genericList;
	
	private Map<String, FieldModel> fields;
	private Map<Signature, MethodModel> methods;
	private Map<ClassModel, Integer> hasARel;
	private Collection<ClassModel> dependsOn;


	/**
	 * Creates an ClassModel and assign its basic properties.
	 *
	 * @param asmServiceProvider
	 * @param asmClassNode
	 * @param important
	 */
	public ClassModel(ClassNode asmClassNode) {
		this.asmClassNode = asmClassNode;
		this.modifier = Modifier.parse(asmClassNode.access);
		this.isFinal = Modifier.parseIsFinal(asmClassNode.access);
		this.classType = ClassType.parse(asmClassNode.access);
		this.name = Type.getObjectType(asmClassNode.name).getClassName();
	}

	public String getName() {
		return name;
	}

	@Override
	public List<String> getStereoTypes() {
		List<String> ls = new ArrayList<>();
		switch (getType()) {
		case INTERFACE:
			ls.add("Interface");
			break;
		case CONCRETE:
			break;
		case ABSTRACT:
			ls.add("Abstract");
			break;
		case ENUM:
			ls.add("Enumeration");
			break;
		}
		return ls;
	}

	public Modifier getModifier() {
		return modifier;
	}

	public ClassType getType() {
		return classType;
	}

	// ===================================================================
	// ---------------- All the lazy initialized fields ------------------
	// ===================================================================

	public List<GenericTypeModel> getGenericList() {
		if (genericList == null) {
			genericList = new ArrayList<>();
			
		}
		return genericList;
	}

	public ClassModel getSuperClass() {
		if (superClass == null && asmClassNode.superName != null)
			superClass = ASMParser.getClassByName(asmClassNode.superName);
		return superClass;
	}

	public Iterable<ClassModel> getInterfaces() {
		if (interfaces == null) {
			interfaces = new ArrayList<>();
			@SuppressWarnings("unchecked")
			List<String> ls = asmClassNode.interfaces;
			for (String s : ls) {
				ClassModel m = ASMParser.getClassByName(s);
				if (m != null)
					interfaces.add(m);
			}
		}
		return interfaces;
	}

	public Map<ClassModel, Integer> getHasRelation() {
		if (hasARel == null) {
			hasARel = new HashMap<>();
			for (FieldModel field : getFields()) {
				ClassModel hasClass = field.getType().getClassModel();
				if (hasClass != null) {
					if (hasARel.containsKey(hasClass)) {
						hasARel.put(hasClass, hasARel.get(hasClass) + 1);
					} else {
						hasARel.put(hasClass, 1);
					}
				}
			}
		}
		return hasARel;
	}

	public Collection<ClassModel> getDependsRelation() {
		if (dependsOn == null) {
			dependsOn = new HashSet<>();
			for (MethodModel method : getMethods()) {
				dependsOn.addAll(method.addDependsClasses());
			}
			dependsOn.removeAll(getHasRelation().keySet());
			dependsOn.remove(this);
			if (getSuperClass() != null)
				dependsOn.remove(getSuperClass());
			getInterfaces().forEach((i) -> dependsOn.remove(i));
		}
		return dependsOn;
	}

	public Iterable<MethodModel> getMethods() {
		return getMethodsMap().values();
	}

	public MethodModel getMethodBySignature(Signature signature) {
		if (getMethodsMap().containsKey(signature))
			return getMethodsMap().get(signature);
		if (getSuperClass() != null)
			getSuperClass().getMethodBySignature(signature);
		return null;
	}

	private Map<Signature, MethodModel> getMethodsMap() {
		if (methods == null) {
			methods = new HashMap<>();
			@SuppressWarnings("unchecked")
			List<MethodNode> ls = asmClassNode.methods;
			for (MethodNode methodNode : ls) {
				MethodModel methodModel = new MethodModel(this, methodNode);
				Signature signature = methodModel.getSignature();
				methods.put(signature, methodModel);
			}
		}
		return methods;
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

	public FieldModel getFieldByName(String name) {
		if (getFieldMap().containsKey(name))
			return getFieldMap().get(name);
		if (getSuperClass() != null)
			return getSuperClass().getFieldByName(name);
		return null;
	}

	public boolean isFinal() {
		return isFinal;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public void visit(IVisitor<ClassModel> IVisitor) {
		IVisitor.visit(this);
	}

}