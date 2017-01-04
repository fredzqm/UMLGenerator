package model.type;

import model.ClassModel;

/**
 * Representing type model in general
 * 
 * @author zhang
 *
 */
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

	/**
	 * 
	 * @return the dimension of this type, 0 if its is not an array
	 */
	default int getDimension() {
		return 0;
	}
}
