package main.model;

import java.util.Collection;

public class TopMethodModel implements MethodModel {
	
	private ClassModel belongsTo;
	private String name;
	private Modifier modifier;

	@Override
	public ClassModel getClassBelong() {
		return belongsTo;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Modifier getModifier() {
		return modifier;
	}

	@Override
	public Collection<TypeModel> getArguments() {
		return null;
	}

	@Override
	public TypeModel getReturnType() {
		return null;
	}

	@Override
	public Collection<MethodModel> getDependMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<FieldModel> getDependFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFinal() {
		// TODO Auto-generated method stub
		return false;
	}

}
