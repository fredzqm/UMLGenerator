package model;

import java.util.*;

/**
 * Representing the type in java programs
 *
 * @author zhang
 */
class ParametizedClassModel implements TypeModel {
    private final TypeModel outterClassType;
    private final ClassModel classModel;
    private final List<TypeModel> genericArgs;
    private List<TypeModel> superTypes;

    ParametizedClassModel(TypeModel outterClassType, ClassModel classModel, List<TypeModel> genericList) {
        if (classModel == null)
            throw new RuntimeException("ClassModel cannot be null");
        this.outterClassType = outterClassType;
        this.classModel = classModel;
        this.genericArgs = genericList;
    }

    ParametizedClassModel(ClassModel classModel, List<TypeModel> genericList) {
        this(null, classModel, genericList);
    }

    public ClassModel getClassModel() {
        return classModel;
    }

    List<TypeModel> getGenericArgs() {
        return genericArgs;
    }

    public TypeModel getGenericArg(int index) {
        return genericArgs.get(index);
    }

    public int getGenericArgNumber() {
        return genericArgs.size();
    }

    public String getName() {
        return classModel.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParametizedClassModel) {
            ParametizedClassModel o = (ParametizedClassModel) obj;
            return classModel == o.classModel && Objects.equals(outterClassType, outterClassType)
                    && genericArgs.equals(o.genericArgs);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return classModel.hashCode() + genericArgs.hashCode() * 31
                + (outterClassType == null ? 0 : outterClassType.hashCode() * 127);
    }

    @Override
    public Iterable<TypeModel> getSuperTypes() {
        if (superTypes == null) {
            List<GenericTypeParam> genels = classModel.getGenericList();
            if (genels.size() != classModel.getGenericList().size())
                throw new RuntimeException("The number of generic arguments and parameters do not match"
                        + classModel.getGenericList() + " " + genels);
            Map<String, TypeModel> paramMap = new HashMap<>();
            if (outterClassType != null) {
                List<GenericTypeParam> outterGenels = outterClassType.getClassModel().getGenericList();
                for (int i = 0; i < outterClassType.getGenericArgNumber(); i++) {
                    GenericTypeParam p = outterGenels.get(i);
                    paramMap.put(p.getName(), outterClassType.getGenericArg(i));
                }
            }
            for (int i = 0; i < getGenericArgNumber(); i++) {
                GenericTypeParam p = genels.get(i);
                paramMap.put(p.getName(), getGenericArg(i));
            }
            superTypes = new ArrayList<>(2);
            for (TypeModel t : classModel.getSuperTypes()) {
                superTypes.add(t.replaceTypeVar(paramMap));
            }
        }
        return superTypes;
    }

    @Override
    public TypeModel replaceTypeVar(Map<String, ? extends TypeModel> paramMap) {
        List<TypeModel> ls = new ArrayList<>(genericArgs.size());
        for (TypeModel t : genericArgs)
            ls.add(t.replaceTypeVar(paramMap));
        return new ParametizedClassModel(outterClassType, classModel, ls);
    }

    @Override
    public TypeModel assignTo(ClassModel clazz) {
        if (getClassModel() == clazz)
            return this;
        return TypeModel.super.assignTo(clazz);
    }

    @Override
    public Collection<ClassModel> getDependentClass() {
        Collection<ClassModel> set = new HashSet<>();
        set.add(classModel);
        for (TypeModel t : genericArgs) {
            if (checkRecursive(t))
                continue;
            set.addAll(t.getDependentClass());
        }
        return set;
    }

    private boolean checkRecursive(TypeModel t) {
        if (t.getGenericArgNumber() > 0) {
            for (int i = 0; i < t.getGenericArgNumber(); i++) {
                TypeModel x = t.getGenericArg(i);
                if (x == this)
                    return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        getSuperTypes();
        if (!genericArgs.isEmpty()) {
            sb.append(genericArgs.get(0));
            for (int i = 1; i < genericArgs.size(); i++) {
                sb.append(",").append(genericArgs.get(i));
            }
        }
        return getName() + "<" + sb.toString() + ">";
    }
}
