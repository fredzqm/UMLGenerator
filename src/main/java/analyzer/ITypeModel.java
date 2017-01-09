package analyzer;

public interface ITypeModel {

	/**
	 * 
	 * @return name of the type
	 */
	String getName();

	/**
	 * 
	 * @return the lower bound class related to this type
	 */
	IClassModel getClassModel();

	/**
	 * @return the dimension of this type, 0 if its is not an array
	 */
	int getDimension();

	/**
	 * @return all the classes that this type depends on
	 */
	Iterable<? extends IClassModel> getTypeDependsOn();

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
	 * 
	 * @param className
	 *            the name of the class
	 * @return the strictest type of className that this type can be assigned to
	 */
	ITypeModel assignTo(String className);
}
