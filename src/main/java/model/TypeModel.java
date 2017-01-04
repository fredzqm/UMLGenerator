package model;

public interface TypeModel {

	/**
	 * @return the class model behind this type model. null if it is a primitive
	 *         type
	 */
	ClassModel getClassModel();

	/**
	 * 
	 * @return the name representing this type
	 */
	String getName();

}
