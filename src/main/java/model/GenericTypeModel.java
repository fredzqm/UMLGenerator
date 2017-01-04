package model;

/**
 * serve as a place holder for generic type, we can should replace it with a
 * concrete class model
 * 
 * @author zhang
 *
 */
class GenericTypeModel implements ClazzTypeModel {
	private final ClazzTypeModel lowerBound;
	private final ClazzTypeModel upperBound;
	private final String key;

	GenericTypeModel(ClazzTypeModel lowerBound, ClazzTypeModel upperBound, String name) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.key = name;
	}

	public ClazzTypeModel getLowerBound() {
		return lowerBound;
	}

	public ClazzTypeModel getUpperBound() {
		return upperBound;
	}

	@Override
	public ClassModel getClassModel() {
		return lowerBound.getClassModel();
	}

	@Override
	public String getName() {
		return key;
	}

	static GenericTypeModel getWildType(String name) {
		return new GenericTypeModel(ClassTypeModel.getObject(), null, name);
	}

	static GenericTypeModel getLowerBounded(ClassTypeModel classTypeModel, String name) {
		return new GenericTypeModel(classTypeModel, null, name);
	}

	static GenericTypeModel getUpperBounded(ClassTypeModel upperBound, String name) {
		return new GenericTypeModel(ClassTypeModel.getObject(), upperBound, name);
	}

}
