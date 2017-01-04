package model;

/**
 * serve as a place holder for generic type, we can should replace it with a
 * concrete class model
 * 
 * @author zhang
 *
 */
class GenericTypeModel implements ClassTypeModel {
	private final ClassTypeModel lowerBound;
	private final ClassTypeModel upperBound;
	private final String key;

	GenericTypeModel(ClassTypeModel lowerBound, ClassTypeModel upperBound, String name) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.key = name;
	}

	public ClassTypeModel getLowerBound() {
		return lowerBound;
	}

	public ClassTypeModel getUpperBound() {
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
		return new GenericTypeModel(ConcreteClassTypeModel.getObject(), null, name);
	}

	static GenericTypeModel getLowerBounded(ClassTypeModel classTypeModel, String name) {
		return new GenericTypeModel(classTypeModel, null, name);
	}

	static GenericTypeModel getUpperBounded(ClassTypeModel upperBound, String name) {
		return new GenericTypeModel(ConcreteClassTypeModel.getObject(), upperBound, name);
	}

}
