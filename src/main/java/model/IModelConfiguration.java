package model;

/**
 * everything model needs to know to parse the model
 * 
 * @author zhang
 *
 */
public interface IModelConfiguration {

	/**
	 * return the name of classes that the model needs to analyzer
	 */
	Iterable<String> getClasses();

	/**
	 * @return true if the Model should recursively explore related classes
	 */
	boolean isRecursive();

}
