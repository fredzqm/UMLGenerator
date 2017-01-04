package model;

import java.util.Map;

import model.type.TypeModel;

public class GenericEnvironment {
	private Map<String,TypeModel> generic;
	
	public TypeModel getTypeModelOf(String genericID) {
		return generic.get(genericID);
	}
}
