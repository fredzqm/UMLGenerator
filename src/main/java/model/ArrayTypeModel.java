package model;

/**
 * A decorate class for array
 * 
 * @author zhang
 *
 */
class ArrayTypeModel implements TypeModel {
	private final TypeModel arrayType;
	private final int dimension;

	ArrayTypeModel(TypeModel arrayType, int dimension) {
		if (arrayType.getClass() == ArrayTypeModel.class) {
			throw new RuntimeException("Array type model cannot be an array of another array");
		}
		this.dimension = dimension;
		this.arrayType = arrayType;
	}

	@Override
	public ClassModel getClassModel() {
		return arrayType.getClassModel();
	}

	@Override
	public String getName() {
		StringBuilder sb = new StringBuilder(arrayType.getName());
		for (int i = 0; i < dimension; i++) {
			sb.append("[]");
		}
		return sb.toString();
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ArrayTypeModel) {
			ArrayTypeModel o = (ArrayTypeModel) obj;
			return dimension == o.dimension && arrayType.equals(o.arrayType);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return dimension * 31 + arrayType.hashCode();
	}
}