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
class GenericTypeVarPlaceHolder extends TypeModel {
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
        return Collections.emptyList();
    }

    @Override
    public TypeModel replaceTypeVar(Map<String, ? extends TypeModel> paramMap) {
        if (paramMap.containsKey(key))
            return paramMap.get(key);
        Logger.logError(getClass().getName() + " GenericTypeVarPlaceHolder " + key + " is not found in the paraMap: "
                + paramMap);
        return this;
    }

    @Override
    public List<ClassModel> getDependentClass() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return getName();
    }

}
