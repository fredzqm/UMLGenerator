package model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * serve as a place holder for generic type, we can should replace it with a
 * concrete class model
 *
 * @author zhang
 */
class GenericTypeParam extends TypeModel {
    private final List<TypeModel> boundSuperTypes;
    private final String key;
    private boolean processed = false;

    GenericTypeParam(String key, List<TypeModel> boundLs) {
        this.boundSuperTypes = boundLs;
        this.key = key;
    }

    List<TypeModel> getBoundSuperTypes() {
        return boundSuperTypes;
    }

    @Override
    public ClassModel getClassModel() {
        if (boundSuperTypes.isEmpty())
            return ASMParser.getObject();
        return boundSuperTypes.get(0).getClassModel();
    }

    @Override
    public String getName() {
        return key;
    }

    @Override
    public Iterable<TypeModel> getSuperTypes() {
        if (boundSuperTypes.isEmpty())
            return Collections.singletonList(ASMParser.getObject());
        return boundSuperTypes;
    }

    @Override
    public TypeModel replaceTypeVar(Map<String, ? extends TypeModel> paramMap) {
        if (!paramMap.containsKey(key)) {
            System.err.println(paramMap + " does not contain " + key);
            return null;
        }
        if (paramMap.get(key) == this) {
            // this is in the params list, modify in place
            if (!processed) {
                ListIterator<TypeModel> itr = boundSuperTypes.listIterator();
                while (itr.hasNext()) {
                    TypeModel t = itr.next();
                    itr.set(t.replaceTypeVar(paramMap));
                }
                processed = true;
            }
            return this;
        } else {
            // this is not in the params list, a place holder to representin
            return paramMap.get(key);
        }
    }

    @Override
    public Collection<ClassModel> getDependentClass() {
        Collection<ClassModel> set = new HashSet<>();
        for (TypeModel t : boundSuperTypes) {
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
    public TypeModel eraseGenericType() {
        return getClassModel();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TypeModel t : boundSuperTypes) {
            if (checkRecursive(t))
                sb.append(" : ? extends " + getName());
            else
                sb.append(" :" + t.toString());
        }
        return "[" + key + sb.toString() + "]";
    }

}
