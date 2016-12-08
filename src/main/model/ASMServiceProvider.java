package main.model;

public interface ASMServiceProvider {
	TypeModel getTypeByName(String name);

	ClassModel getClassByName(String name);
}
