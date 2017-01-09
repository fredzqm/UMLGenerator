package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import analyzer.IClassModel;
import model.TypeParser.ClassSignatureParseResult;
import utility.ClassType;
import utility.IMapper;
import utility.Modifier;

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
class ClassModel implements IClassModel, TypeModel {
	private final ClassNode asmClassNode;

	private final Modifier modifier;
	private final boolean isFinal;
	private final ClassType classType;
	private final String name;

	private List<TypeModel> superTypes;
	private List<GenericTypeParam> genericParams;

	private Map<String, FieldModel> fields;
	private Map<Signature, MethodModel> methods;

	private Map<String, GenericTypeParam> paramMap;
	private Collection<ClassModel> hasTypes;
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

	public Modifier getModifier() {
		return modifier;
	}

	@Override
	public ClassModel getClassModel() {
		return this;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public ClassType getType() {
		return classType;
	}

	// ===================================================================
	// ---------------- All the lazy initialized fields ------------------
	// ===================================================================

	public List<GenericTypeParam> getGenericList() {
		if (genericParams == null) {
			getSuperTypes();
		}
		return genericParams;
	}

	public List<TypeModel> getSuperTypes() {
		if (superTypes == null) {
			if (asmClassNode.signature == null) {
				genericParams = Collections.emptyList();
				superTypes = new ArrayList<>();
				// add super class
				if (asmClassNode.superName != null) {
					TypeModel superClass = ASMParser.getClassByName(asmClassNode.superName);
					superTypes.add(superClass);
				}
				// add interfaces
				@SuppressWarnings("unchecked")
				List<String> ls = asmClassNode.interfaces;
				for (String s : ls) {
					TypeModel m = ASMParser.getClassByName(s);
					if (m != null)
						superTypes.add(m);
				}
			} else {
				ClassSignatureParseResult rs = TypeParser.parseClassSignature(asmClassNode.signature);
				genericParams = rs.getParamsList();
				superTypes = rs.getSuperTypes();

				// replace GenericTypeVarPlaceHolder with GenericTypeParams
				Map<String, GenericTypeParam> paramMap = getParamsMap();
				for (GenericTypeParam t : genericParams) {
					t.replaceTypeVar(paramMap);
				}
				ListIterator<TypeModel> itr = superTypes.listIterator();
				while (itr.hasNext()) {
					TypeModel t = itr.next();
					itr.set(t.replaceTypeVar(paramMap));
				}
			}
		}
		return superTypes;
	}

	Map<String, GenericTypeParam> getParamsMap() {
		if (paramMap == null) {
			paramMap = new HashMap<>();
			for (GenericTypeParam p : getGenericList()) {
				paramMap.put(p.getName(), p);
			}
		}
		return paramMap;
	}

	public ClassModel getSuperClass() {
		List<TypeModel> ls = getSuperTypes();
		if (ls.isEmpty())
			return null;
		return ls.get(0).getClassModel();
	}

	public Iterable<ClassModel> getInterfaces() {
		List<TypeModel> ls = getSuperTypes();
		if (ls.isEmpty())
			return Collections.emptyList();
		IMapper<TypeModel, ClassModel> map = (c) -> c.getClassModel();
		return map.map(ls.subList(1, ls.size()));
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

	public Collection<ClassModel> getHasTypes() {
		if (hasTypes == null) {
			hasTypes = new HashSet<>();
			for (FieldModel field : getFields()) {
				TypeModel type = field.getFieldType();
				hasTypes.addAll(type.getDependsOn());
			}
		}
		return hasTypes;
	}

	public Collection<ClassModel> getClassDependsOn() {
		if (dependsOn == null) {
			dependsOn = new HashSet<>();
			for (MethodModel method : getMethods()) {
				dependsOn.addAll(method.getDependsClasses());
			}
			if (getSuperClass() != null)
				dependsOn.remove(getSuperClass());
			getInterfaces().forEach((i) -> dependsOn.remove(i));
			getHasTypes().forEach((t) -> dependsOn.remove(t));
			dependsOn.remove(this);
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

	@Override
	public String getLabel() {
		return getName();
	}

	@Override
	public List<ClassModel> getDependsOn() {
		return Arrays.asList(this);
	}

	@Override
	public String toString() {
		return getName();
	}

}