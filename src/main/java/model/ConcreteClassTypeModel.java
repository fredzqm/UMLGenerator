package model;

import java.util.List;

/**
 * Representing the type in java programs
 *
 * @author zhang
 */
class ConcreteClassTypeModel implements ClassTypeModel {
	private final ClassModel classModel;
	private final List<ConcreteClassTypeModel> genericList;

	ConcreteClassTypeModel(ClassModel classModel, List<ConcreteClassTypeModel> genericList) {
		this.classModel = classModel;
		this.genericList = genericList;
	}

	public ClassModel getClassModel() {
		return classModel;
	}

	public List<ConcreteClassTypeModel> getGenericList() {
		return genericList;
	}

	public String getName() {
		return classModel.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConcreteClassTypeModel) {
			ConcreteClassTypeModel o = (ConcreteClassTypeModel) obj;
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

	public static ClassTypeModel getObject() {
		return TypeParser.getType(ASMParser.getObject());
	}

}
