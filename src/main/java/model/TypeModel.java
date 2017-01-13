package model;

import analyzer.ITypeModel;

import java.util.Collection;
import java.util.Map;

/**
 * Representing type model in general
 *
 * @author zhang
 */
abstract class TypeModel implements ITypeModel {

    /**
     * For generic type, this would return a lower bound of this type
     *
     * @return the class model behind this type model. null if it is a primitive
     *         type
     */
    public abstract ClassModel getClassModel();

    /**
     * @return the name representing this type
     */
    public abstract String getName();

    /**
     * @return the dimension of this type, 0 if its is not an array
     */
    public int getDimension() {
        return 0;
    }

    /**
     * @return all the classes that this type depends on
     */
    public abstract Collection<ClassModel> getDependentClass();

    /**
     * @param index
     * @return the generic argument at specific index
     */
    public TypeModel getGenericArg(int index) {
        throw new RuntimeException();
    }

    /**
     * @return the number of generic argument
     */
    public int getGenericArgNumber() {
        return 0;
    }

    /**
     * 
     * @return if the bound of a wild character type
     */
    public ITypeModel getLowerBound() {
        return null;
    }

    /**
     * 
     * @return
     */
    public ITypeModel getUpperBound() {
        return null;
    }

    /**
     * 
     * @return
     */
    public boolean isWildCharacter() {
        return false;
    }

    /**
     * @return the collection of types that this type can be directly assigned
     *         to
     */
    public abstract Iterable<TypeModel> getSuperTypes();

    /**
     * replace {@link GenericTypeVarPlaceHolder} with real type in the parameter
     * This method should be called once after generic types are first parsed
     *
     * @param paramMap
     *            the parameter type map containing information about each type
     */
    public TypeModel replaceTypeVar(Map<String, ? extends TypeModel> paramMap) {
        return this;
    }

    /**
     * The most strict version of clazz that it can be assigned to
     *
     * @param clazz
     * @return
     */
    public TypeModel assignTo(ClassModel clazz) {
        if (this == clazz)
            return clazz;
        for (TypeModel sup : getSuperTypes()) {
            TypeModel t = sup.assignTo(clazz);
            if (t != null)
                return t;
        }
        return null;
    }

    public TypeModel assignTo(String className) {
        return assignTo(ASMParser.getClassByName(className));
    }

    /**
     * {@link Signature} used by signature to erase the generic type so that we
     * do not have to specify the generic type when getting a getting a method
     * with signature {@link ClassModel#getMethodBySignature(Signature)}
     * 
     * @return
     */
    public TypeModel eraseGenericType() {
        return this;
    }

}