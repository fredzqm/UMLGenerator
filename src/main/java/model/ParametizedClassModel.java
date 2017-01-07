package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representing the type in java programs
 *
 * @author zhang
 */
class ParametizedClassModel implements TypeModel {
	private final ClassModel classModel;
	private final List<TypeModel> genericArgs;
	private List<TypeModel> superTypes;

	ParametizedClassModel(ClassModel classModel, List<TypeModel> genericList) {
		if (classModel == null)
			throw new RuntimeException("ClassModel cannot be null");
		this.classModel = classModel;
		this.genericArgs = genericList;
	}

	public ClassModel getClassModel() {
		return classModel;
	}

	public List<TypeModel> getGenericArgs() {
		return genericArgs;
	}

	public TypeModel getGenericArg(int index) {
		return genericArgs.get(index);
	}

	public int getGenericArgLength() {
		return genericArgs.size();
	}

	public String getName() {
		return classModel.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ParametizedClassModel) {
			ParametizedClassModel o = (ParametizedClassModel) obj;
			return classModel == o.classModel && genericArgs.equals(o.genericArgs);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return classModel.hashCode();
	}

	@Override
	public String toString() {
		return getName() + "<" + genericArgs + ">";
	}

	@Override
	public Iterable<TypeModel> getSuperTypes() {
		if (superTypes == null) {
			List<GenericTypeParam> genels = classModel.getGenericList();
			if (genels.size() != classModel.getGenericList().size())
				throw new RuntimeException("The number of generic arguments and parameters do not match"
						+ classModel.getGenericList() + " " + genels);
			Map<String, TypeModel> paramMap = new HashMap<>();
			for (int i = 0; i < genericArgs.size(); i++) {
				GenericTypeParam p = genels.get(i);
				paramMap.put(p.getName(), genericArgs.get(i));
			}
			superTypes = new ArrayList<>();
			for (TypeModel t : classModel.getSuperTypes()) {
				superTypes.add(t.replaceTypeVar(paramMap));
			}
		}
		return superTypes;
	}

	@Override
	public TypeModel replaceTypeVar(Map<String, ? extends TypeModel> paramMap) {
		List<TypeModel> ls = new ArrayList<>();
		for (TypeModel t : genericArgs)
			ls.add(t.replaceTypeVar(paramMap));
		return new ParametizedClassModel(classModel, ls);
	}

	@Override
	public TypeModel assignTo(ClassModel clazz) {
		if (getClassModel() == clazz)
			return this;
		return TypeModel.super.assignTo(clazz);
	}
}
