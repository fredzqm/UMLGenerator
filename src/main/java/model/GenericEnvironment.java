package model;

import java.util.Map;

public class GenericEnvironment {
	private Map<String,TypeModel> generic;
	
	public TypeModel getTypeModelOf(String genericID) {
		return generic.get(genericID);
	}

	public static GenericEnvironment parse(String substring) {
		return null;
	}
}
