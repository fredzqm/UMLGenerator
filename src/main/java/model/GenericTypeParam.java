package model;

import java.util.List;

/**
 * serve as a place holder for generic type, we can should replace it with a
 * concrete class model
 * 
 * @author zhang
 *
 */
class GenericTypeParam implements TypeModel {
	private final List<TypeModel> boundSuperTypes;
	private final String key;

	public GenericTypeParam(String key, List<TypeModel> boundLs) {
		this.boundSuperTypes = boundLs;
		this.key = key;
	}

	public List<TypeModel> getBoundSuperTypes() {
		return boundSuperTypes;
	}

	@Override
	public ClassModel getClassModel() {
		if (boundSuperTypes.isEmpty())
			return ASMParser.getObject();
		return boundSuperTypes.get(0).getClassModel();
	}

	@Override
	public String getName() {
		return key;
	}

}
