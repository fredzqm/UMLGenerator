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
	Iterable<? extends IClassModel> getDependsOn();

	/**
	 * 
	 * @param index
	 * @return the generic argument at specific index
	 */
	ITypeModel getGenericArg(int index);

	/**
	 * 
	 * @return the number of generic argument
	 */
	int getGenericArgNumber();

	/**
	 * 
	 * @param className
	 *            the name of the class
	 * @return the strictest type of className that this type can be assigned to
	 */
	ITypeModel assignTo(String className);
}
