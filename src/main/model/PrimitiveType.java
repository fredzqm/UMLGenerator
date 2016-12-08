package main.model;

public enum PrimitiveType {
	INT, DOUBLE, FLOAT, BOOLEAN, BYTE, CHAR, SHORT, LONG, NON_PRIMITIVE;
	
	String getName(){
		switch(this){
		case INT:
			return "int";
		case DOUBLE:
			return "double";
		case FLOAT:
			return "float";
		case BOOLEAN:
			return "boolean";
		case BYTE:
			return "byte";
		case CHAR:
			return "char";
		case SHORT:
			return "short";
		case LONG:
			return "long";
		case NON_PRIMITIVE:
			return "Non_primitive";
		default:
			throw new RuntimeException("PrimitiveType.getName(): We missed " + this);
		}
	}
}
