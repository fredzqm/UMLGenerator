package model;

/**
 * serve as a place holder for generic type, we can should replace it with a
 * concrete class model
 * 
 * @author zhang
 *
 */
class GenericTypeParams implements TypeModel {
	private final TypeModel lowerBound;
	private final TypeModel upperBound;

	GenericTypeParams(TypeModel lowerBound, TypeModel upperBound) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	public TypeModel getLowerBound() {
		return lowerBound;
	}

	public TypeModel getUpperBound() {
		return upperBound;
	}

	@Override
	public ClassModel getClassModel() {
		return lowerBound.getClassModel();
	}

	@Override
	public String getName() {
		return null;
	}

	private static GenericTypeModel wildType = new GenericTypeModel(null, null);

	public static GenericTypeModel getWildType() {
		return wildType;
	}

	static GenericTypeModel getLowerBounded(TypeModel classTypeModel) {
		return new GenericTypeModel(classTypeModel, null);
	}

	static GenericTypeModel getUpperBounded(TypeModel upperBound) {
		return new GenericTypeModel(ASMParser.getObject(), upperBound);
	}

}
