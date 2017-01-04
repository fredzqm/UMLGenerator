package model;

/**
 * A place holder for generic class, so we know that we should get this class
 * from its outer generic environment
 * 
 * @author zhang
 *
 */
public class GenericTypeVar implements TypeModel {
	private final String key;

	public GenericTypeVar(String name) {
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

}
