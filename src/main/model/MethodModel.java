package main.model;

import java.util.Collection;

public interface MethodModel {
	ClassModel getClassBelong();

	String getName();

	Modifier getModifier();

	Collection<TypeModel> getArguments();

	TypeModel getReturnType();

	Collection<MethodModel> getDependMethods();

	Collection<FieldModel> getDependFields();

	boolean isFinal();

}
