package model;

import java.util.List;

/**
 * Representing the type in java programs
 *
 * @author zhang
 */
class ParametizedClassModel implements TypeModel {
	private final ClassModel classModel;
	private final List<TypeModel> genericArguments;

	ParametizedClassModel(ClassModel classModel, List<TypeModel> genericList) {
		if (classModel == null)
			throw new RuntimeException("ClassModel cannot be null");
		this.classModel = classModel;
		this.genericArguments = genericList;
	}

	public ClassModel getClassModel() {
		return classModel;
	}

	public List<TypeModel> getGenericList() {
		return genericArguments;
	}

	public String getName() {
		return classModel.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ParametizedClassModel) {
			ParametizedClassModel o = (ParametizedClassModel) obj;
			return classModel == o.classModel;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return classModel.hashCode();
	}

	@Override
	public String toString() {
		return getName();
	}


}
