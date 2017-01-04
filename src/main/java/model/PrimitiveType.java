package model;

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

}
