package main.model;

/**
 * 
 * @author zhang
 *
 */
public class TypeModel {
	private ClassModel classModel;
	private int dimension;
	private PrimitiveType primiType;

	public TypeModel(ClassModel model, int dimen) {
		classModel = model;
		dimension = dimen;
		primiType = PrimitiveType.NON_PRIMITIVE;
	}

	public TypeModel(PrimitiveType type, int dimen) {
		classModel = null;
		dimension = dimen;
		primiType = type;
	}

	public ClassModel getClassModel() {
		return classModel;
	}

	public void setClassModel(ClassModel classModel) {
		this.classModel = classModel;
	}

	public int getDimension() {
		return dimension;
	}

	public String getName() {
		if (primiType == PrimitiveType.NON_PRIMITIVE)
			return primiType.getName();
		return classModel.getName();
	}
}
