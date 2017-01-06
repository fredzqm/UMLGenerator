package model;

import java.util.Collections;

/**
 * representing any primitive type
 * 
 * @author zhang
 *
 */
enum PrimitiveType implements TypeModel {
	INT("int"), DOUBLE("double"), FLOAT("float"), BOOLEAN("boolean"), BYTE("byte"), CHAR("char"), SHORT("short"), LONG(
			"long"), VOID("void");

	private final String name;

	private PrimitiveType(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ClassModel getClassModel() {
		return null;
	}

	public static TypeModel parseTypeModel(char x) {
		switch (x) {
		case 'Z':
			return BOOLEAN;
		case 'C':
			return CHAR;
		case 'B':
			return BYTE;
		case 'S':
			return SHORT;
		case 'I':
			return INT;
		case 'F':
			return FLOAT;
		case 'J':
			return LONG;
		case 'D':
			return DOUBLE;
		default:
			throw new RuntimeException(x + " Cannot represent a primitive type");
		}
	}

	@Override
	public Iterable<TypeModel> getSuperTypes() {
		return Collections.EMPTY_LIST;
	}

}
