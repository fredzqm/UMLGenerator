package analyzer;

public interface ITypeModel {

    /**
     * @return name of the type
     */
    String getName();

    /**
     * @return the lower bound class related to this type
     */
    IClassModel getClassModel();

    /**
     * @return the dimension of this type, 0 if its is not an array
     */
    int getDimension();

    /**
     * @param index
     * @return the generic argument at specific index
     */
    ITypeModel getGenericArg(int index);

    /**
     * @return the number of generic argument
     */
    int getGenericArgNumber();

    /**
     * 
     * @return if the bound of a wild character type
     */
    ITypeModel getLowerBound();

    /**
     * 
     * @return
     */
    ITypeModel getUpperBound();
    
    /**
     * 
     * @return
     */
    boolean isWildCharacter();
    
    /**
     * @param className
     *            the name of the class
     * @return the strictest type of className that this type can be assigned to
     */
    ITypeModel assignTo(String className);
}
