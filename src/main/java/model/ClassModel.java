package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InnerClassNode;
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
    private final boolean isStatic;
    private final ClassType classType;
    private final String name;
    private final ClassModel outterClass;

    private List<TypeModel> superTypes;
    private List<GenericTypeParam> genericParams;

    private Map<String, FieldModel> fields;
    private Map<Signature, MethodModel> methods;

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
        int access = getAccess(asmClassNode);
        this.modifier = Modifier.parse(access);
        this.isFinal = Modifier.parseIsFinal(access);
        this.classType = ClassType.parse(access);
        this.isStatic = parseIsStatic();
        this.name = Type.getObjectType(asmClassNode.name).getClassName();
        int index = name.lastIndexOf('$');
        this.outterClass = index < 0 ? null : ASMParser.getClassByName(name.substring(0, index));
    }

    private boolean parseIsStatic() {
        for (FieldNode field : (List<FieldNode>) asmClassNode.fields) {
            if (field.name.startsWith("this$") && (field.access & Opcodes.ACC_SYNTHETIC) != 0) {
                return false;
            }
        }
        return true;
    }

    private int getAccess(ClassNode asmClassNode) {
        for (InnerClassNode inner : (List<InnerClassNode>) asmClassNode.innerClasses)
            if (asmClassNode.name.equals(inner.name))
                return inner.access;
        return asmClassNode.access | Opcodes.ACC_STATIC;
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

    public boolean isStatic() {
        return isStatic;
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
                superTypes = new ArrayList<>(2);
                // add super class
                if (asmClassNode.superName != null) {
                    TypeModel superClass = ASMParser.getClassByName(asmClassNode.superName);
                    superTypes.add(superClass);
                }
                // add interfaces
                for (String s : (List<String>) asmClassNode.interfaces) {
                    TypeModel m = ASMParser.getClassByName(s);
                    if (m != null)
                        superTypes.add(m);
                }
            } else {
                ClassSignatureParseResult rs = TypeParser.parseClassSignature(asmClassNode.signature);
                genericParams = rs.getParamsList();
                Map<String, GenericTypeParam> paramMap = getParamsMap();
                for (GenericTypeParam t : genericParams)
                    t.replaceTypeVar(paramMap);
                superTypes = new ArrayList<>(rs.getSuperTypes().size());
                for (TypeModel t : rs.getSuperTypes())
                    superTypes.add(t.replaceTypeVar(paramMap));
            }
        }
        return superTypes;
    }

    Map<String, GenericTypeParam> getParamsMap() {
        List<GenericTypeParam> genList = getGenericList();
        Map<String, GenericTypeParam> paramMap = outterClass == null ? new HashMap<>(genList.size())
                : outterClass.getParamsMap();
        for (GenericTypeParam p : genList) {
            paramMap.put(p.getName(), p);
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
        List<String> ls = new ArrayList<>(1);
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

    public Collection<ClassModel> getFieldClasses() {
        if (hasTypes == null) {
            hasTypes = new HashSet<>();
            for (FieldModel field : getFields()) {
                TypeModel type = field.getFieldType();
                hasTypes.addAll(type.getDirectDependsOnClass());
            }
        }
        return hasTypes;
    }

    public Collection<ClassModel> getDependsOnClasses() {
        if (dependsOn == null) {
            dependsOn = new HashSet<>(getFieldClasses());
            for (MethodModel method : getMethods()) {
                dependsOn.addAll(method.getDependentClasses());
            }
            if (getSuperClass() != null)
                dependsOn.remove(getSuperClass());
            getInterfaces().forEach((i) -> dependsOn.remove(i));
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
    public String toString() {
        return getName();
    }

    @Override
    public List<ClassModel> getDirectDependsOnClass() {
        return Arrays.asList(this);
    }

}