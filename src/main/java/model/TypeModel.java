package model;

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

//	/**
//	 * It would recursively check if this type can fit in x
//	 * 
//	 * @param b
//	 * @return true if a variable of type b can be assigned to this type
//	 */
//	boolean assignable(TypeModel b);
//
//	
//	static boolean assignable(TypeModel a, TypeModel b) {
//		if (a == b)
//			return true;
//		if (a.getDimension() != b.getDimension())
//			return false;
//		ClassModel tc = a.getClassModel();
//		ClassModel xc = b.getClassModel();
//		if (tc == null) {
//			if (xc != null)
//				return false;
//		} else {
//			if (!tc.assignable(xc))
//				return false;
//		}
//		return a.assignable(b);
//	}
//	
//	 /**
//	 * It would check if x is a super type of this and finds the most strict
//	 * generic combination that is assignable from this.
//	 *
//	 * It can return an {@link ArrayTypeModel} if this is an array itself
//	 * {@link ParametizedClassModel} if this is a parametized type
//	 *
//	 * @param x
//	 * @return the most strict form of class x that is assignable from this
//	 */
//	 TypeModel assignTo(ClassModel clazz);
}
