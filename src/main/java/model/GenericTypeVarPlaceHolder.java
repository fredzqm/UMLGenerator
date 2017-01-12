package model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A place holder for generic class, so we know that we should get this class
 * from its outer generic environment
 *
 * @author zhang
 */
class GenericTypeVarPlaceHolder implements TypeModel {
    private final String key;

    GenericTypeVarPlaceHolder(String name) {
        key = name;
    }

    @Override
    public ClassModel getClassModel() {
        return null;
    }

    @Override
    public String getName() {
        return key;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GenericTypeVarPlaceHolder) {
            GenericTypeVarPlaceHolder o = (GenericTypeVarPlaceHolder) obj;
            return key.equals(o.key);
        }
        return false;
    }

    @Override
    public Iterable<TypeModel> getSuperTypes() {
        System.err.println("GenericTypeVar does not know its super types");
        return Collections.emptyList();
    }

    @Override
    public TypeModel replaceTypeVar(Map<String, ? extends TypeModel> paramMap) {
        if (paramMap.containsKey(key))
            return paramMap.get(key);
        throw new RuntimeException("GenericTypeVarPlaceHolder " + key + " is not found in the paraMap: " + paramMap);
    }

    @Override
    public List<ClassModel> getDependentClass() {
        throw new RuntimeException("GenericTypeVarPlaceHolder does not know what it depends on");
    }

    @Override
    public String toString() {
        return getName();
    }

}
