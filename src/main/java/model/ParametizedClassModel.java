package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import utility.IMapper;

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

	public List<TypeModel> getGenericArgs() {
		return genericArguments;
	}

	public String getName() {
		return classModel.getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ParametizedClassModel) {
			ParametizedClassModel o = (ParametizedClassModel) obj;
			return classModel == o.classModel && genericArguments.equals(o.genericArguments);
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

	@Override
	public Iterable<TypeModel> getSuperTypes() {
		IMapper<TypeModel, TypeModel> mapper = (x) -> {
			return null;
		};
		return mapper.map(classModel.getSuperTypes());
	}

	@Override
	public TypeModel replaceTypeVar(Map<String, ? extends TypeModel> paramMap) {
		List<TypeModel> ls = new ArrayList<>();
		for (TypeModel t : genericArguments)
			ls.add(t.replaceTypeVar(paramMap));
		return new ParametizedClassModel(classModel, ls);
	}
}
