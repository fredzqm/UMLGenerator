package model;

/**
 * A place holder for generic class, so we know that we should get this class
 * from its outer generic environment
 * 
 * @author zhang
 *
 */
public class GenericTypePlaceHolder implements ClassTypeModel {
	private final String key;

	public GenericTypePlaceHolder(String name) {
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
