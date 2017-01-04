package model.type;

import java.util.List;

import model.ClassModel;

/**
 * Representing the type in java programs
 *
 * @author zhang
 */
class ClassTypeModel implements ClazzTypeModel {
	private final ClassModel classModel;
	private final List<ClassTypeModel> genericList;

	ClassTypeModel(ClassModel classModel, List<ClassTypeModel> genericList) {
		this.classModel = classModel;
		this.genericList = genericList;
	}

	public ClassModel getClassModel() {
		return classModel;
	}

	public List<ClassTypeModel> getGenericList() {
		return genericList;
	}

	public String getName() {
		return classModel.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ClassTypeModel) {
			ClassTypeModel o = (ClassTypeModel) obj;
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
