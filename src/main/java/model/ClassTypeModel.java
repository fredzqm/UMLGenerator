package model;

import java.util.Collections;
import java.util.List;

import org.objectweb.asm.Type;

/**
 * Representing the type in java programs
 *
 * @author zhang
 */
class ClassTypeModel implements TypeModel{
	private final TypeType type;
	private final ClassModel classModel;
	private final int dimension;
	private final List<TypeModel> genericList;

	private ClassTypeModel(TypeType type, ClassModel classModel, int dimension, List<TypeModel> genericList) {
		this.type = type;
		this.classModel = classModel;
		this.dimension = dimension;
		this.genericList = genericList;
	}

	/**
	 * convert asm's type instance to TypeModel, assume this type has empty
	 * generic list
	 *
	 * @param type
	 * @return
	 */
	public static TypeModel parse(Type type) {
		int dimension = 0;
		if (type.getSort() == Type.ARRAY) {
			dimension = type.getDimensions();
			type = type.getElementType();
		}
		TypeType primiType = TypeType.parse(type);
		ClassModel classModel;
		if (primiType == TypeType.OBJECT)
			classModel = ASMParser.getClassByName(type.getClassName());
		else
			classModel = null;
		return new ClassTypeModel(primiType, classModel, dimension, Collections.EMPTY_LIST);
	}

	/**
	 * get the type model for a specific class
	 *
	 * @param classModel
	 * @return
	 */
	public static TypeModel getType(ClassModel classModel) {
		return getInstance(classModel, 0, Collections.EMPTY_LIST);
	}

	/**
	 * get the type model for a generic class given its generic list
	 *
	 * @param classModel
	 * @return
	 */
	public static TypeModel getGenericType(ClassModel classModel, List<TypeModel> genericList) {
		return getInstance(classModel, 0, genericList);
	}
	
	/**
	 * get the array type model for a specific class
	 *
	 * @param classModel
	 * @param dimension
	 *            the dimension of the array
	 * @return
	 */
	public static TypeModel getArrayType(ClassModel classModel, int dimension) {
		return getInstance(classModel, dimension, Collections.EMPTY_LIST);
	}

	private static TypeModel getInstance(ClassModel classModel, int dimension, List<TypeModel> genericList) {
		return new ClassTypeModel(TypeType.OBJECT, classModel, dimension, genericList);
	}
	

	public ClassModel getClassModel() {
		return classModel;
	}

	/**
	 * 
	 * @return the dimension of this type, 0 if its is not an array
	 */
	public int getDimension() {
		return dimension;
	}

	public String getName() {
		StringBuilder sb = new StringBuilder();
		if (classModel != null)
			sb.append(classModel.getName());
		else
			sb.append(type.getName());
		for (int i = 0; i < dimension; i++) {
			sb.append("[]");
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ClassTypeModel) {
			ClassTypeModel o = (ClassTypeModel) obj;
			return classModel == o.classModel && type == o.type && dimension == o.dimension;
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (classModel == null)
			return type.hashCode() + dimension;
		return classModel.hashCode() + type.hashCode() + dimension;
	}

	@Override
	public String toString() {
		return getName();
	}

	private enum TypeType {
		INT, DOUBLE, FLOAT, BOOLEAN, BYTE, CHAR, SHORT, LONG, OBJECT, VOID;

		public static TypeType parse(Type type) {
			switch (type.getSort()) {
			case Type.OBJECT:
				return OBJECT;
			case Type.VOID:
				return VOID;
			case Type.INT:
				return INT;
			case Type.DOUBLE:
				return DOUBLE;
			case Type.CHAR:
				return CHAR;
			case Type.BOOLEAN:
				return BOOLEAN;
			case Type.LONG:
				return LONG;
			case Type.BYTE:
				return BYTE;
			case Type.SHORT:
				return SHORT;
			case Type.FLOAT:
				return FLOAT;
			case Type.ARRAY:
				// Should be handled before this method.
			default:
				throw new RuntimeException("does not suport type sort " + type.getClassName());
			}
		}

		String getName() {
			switch (this) {
			case VOID:
				return "void";
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
			case OBJECT:
				return "Object";
			default:
				throw new RuntimeException(" getName(): We missed " + this);
			}
		}
	}
}
