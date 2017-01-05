package model;

import java.util.Map;

/**
 * Representing type model in general
 * 
 * @author zhang
 *
 */
interface TypeModel {

	/**
	 * For generic type, this would return a lower bound of this type
	 * 
	 * @return the class model behind this type model. null if it is a primitive
	 *         type
	 */
	ClassModel getClassModel();

	/**
	 * 
	 * @return the name representing this type
	 */
	String getName();

	/**
	 * 
	 * @return the dimension of this type, 0 if its is not an array
	 */
	default int getDimension() {
		return 0;
	}

	/**
	 * 
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
}
