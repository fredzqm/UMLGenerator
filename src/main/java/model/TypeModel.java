package model;

import java.util.Collection;
import java.util.Map;

import analyzer.ITypeModel;

/**
 * Representing type model in general
 *
 * @author zhang
 */
interface TypeModel extends ITypeModel {

    /**
     * For generic type, this would return a lower bound of this type
     *
     * @return the class model behind this type model. null if it is a primitive
     *         type
     */
    ClassModel getClassModel();

    /**
     * @return the name representing this type
     */
    String getName();

    /**
     * @return the dimension of this type, 0 if its is not an array
     */
    default int getDimension() {
        return 0;
    }

    /**
     * @return all the classes that this type depends on
     */
    Collection<ClassModel> getDependentOnClass();

    /**
     * 
     * @param index
     * @return the generic argument at specific index
     */
    default ITypeModel getGenericArg(int index) {
        throw new RuntimeException();
    }

    /**
     * 
     * @return the number of generic argument
     */
    default int getGenericArgNumber() {
        return 0;
    }
    
    /**
     * @return the collection of types that this type can be directly assigned
     *         to
     */
    Iterable<TypeModel> getSuperTypes();

    /**
     * replace {@link GenericTypeVarPlaceHolder} with real type in the parameter
     * This method should be called once after generic types are first parsed
     *
     * @param paramMap
     *            the parameter type map containing information about each type
     */
    default TypeModel replaceTypeVar(Map<String, ? extends TypeModel> paramMap) {
        return this;
    }

    /**
     * The most strict version of clazz that it can be assigned to
     *
     * @param clazz
     * @return
     */
    default TypeModel assignTo(ClassModel clazz) {
        if (this == clazz)
            return clazz;
        for (TypeModel sup : getSuperTypes()) {
            TypeModel t = sup.assignTo(clazz);
            if (t != null)
                return t;
        }
        return null;
    }

    default ITypeModel assignTo(String className) {
        return assignTo(ASMParser.getClassByName(className));
    }

}
