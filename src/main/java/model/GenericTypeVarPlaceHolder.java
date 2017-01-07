package model;

import java.util.Collections;
import java.util.Map;

/**
 * A place holder for generic class, so we know that we should get this class
 * from its outer generic environment
 * 
 * @author zhang
 *
 */
class GenericTypeVarPlaceHolder implements TypeModel {
	private final String key;

	public GenericTypeVarPlaceHolder(String name) {
		key = name;
	}

	@Override
	public ClassModel getClassModel() {
		return null;
	}

	@Override
	public String getName() {
		return key;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GenericTypeVarPlaceHolder) {
			GenericTypeVarPlaceHolder o = (GenericTypeVarPlaceHolder) obj;
			return key.equals(o.key);
		}
		return false;
	}

	@Override
	public Iterable<TypeModel> getSuperTypes() {
		System.err.println("GenericTypeVar does not know its super types");
		return Collections.EMPTY_LIST;
	}

	@Override
	public TypeModel replaceTypeVar(Map<String, ? extends TypeModel> paramMap) {
		if (paramMap.containsKey(key))
			return paramMap.get(key);
		// cannot fix it because it related to inner class
		System.err.println("GenericTypeVar " + key + " is not found in the paraMap: " + paramMap);
		return this;
	}
}
