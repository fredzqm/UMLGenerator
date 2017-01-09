package model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * serve as a place holder for generic type, we can should replace it with a
 * concrete class model
 *
 * @author zhang
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

	@Override
	public Iterable<TypeModel> getSuperTypes() {
		if (boundSuperTypes.isEmpty())
			return Arrays.asList(ASMParser.getObject());
		return boundSuperTypes;
	}

	@Override
	public TypeModel replaceTypeVar(Map<String, ? extends TypeModel> paramMap) {
		if (!paramMap.containsKey(key)) {
			System.err.println(paramMap + " does not contain " + key);
			return null;
		}
		if (paramMap.get(key) == this) {
			// this is in the params list, modify in place
			ListIterator<TypeModel> itr = boundSuperTypes.listIterator();
			while (itr.hasNext()) {
				TypeModel t = itr.next();
				itr.set(t.replaceTypeVar(paramMap));
			}
			return this;
		} else {
			// this is not in the params list, a place holder to representin
			return paramMap.get(key);
		}
	}

	@Override
	public Collection<ClassModel> getDependsOn() {
		Collection<ClassModel> set = new HashSet<>();
		for (TypeModel t : boundSuperTypes) {
			set.addAll(t.getDependsOn());
		}
		return set;
	}

    @Override
    public String toString() {
    	return this.key + " : " + boundSuperTypes;
    }

}
