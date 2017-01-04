package model.type;

import java.util.Collections;
import java.util.List;

import org.objectweb.asm.Type;

import model.ASMParser;
import model.ClassModel;

/**
 * A factory method utility for type model
 * 
 * @author zhang
 *
 */
public class TypeParser {

	/**
	 * convert asm's type instance to TypeModel, assume this type has empty
	 * generic list
	 *
	 * @param type
	 * @return the corresponding type model
	 */
	public static TypeModel parse(Type type) {
		switch (type.getSort()) {
		case Type.ARRAY:
			int dimension = type.getDimensions();
			type = type.getElementType();
			return new ArrayTypeModel(parse(type), dimension);
		case Type.OBJECT:
			ClassModel classModel = ASMParser.getClassByName(type.getClassName());
			return new ClassTypeModel(classModel, Collections.EMPTY_LIST);
		case Type.VOID:
			return PrimitiveType.VOID;
		case Type.INT:
			return PrimitiveType.INT;
		case Type.DOUBLE:
			return PrimitiveType.DOUBLE;
		case Type.CHAR:
			return PrimitiveType.CHAR;
		case Type.BOOLEAN:
			return PrimitiveType.BOOLEAN;
		case Type.LONG:
			return PrimitiveType.LONG;
		case Type.BYTE:
			return PrimitiveType.BYTE;
		case Type.SHORT:
			return PrimitiveType.SHORT;
		case Type.FLOAT:
			return PrimitiveType.FLOAT;
		default:
			throw new RuntimeException("does not suport type sort " + type.getClassName());
		}
	}

	/**
	 * get the type model for a specific class
	 *
	 * @param classModel
	 * @return
	 */
	public static ClassTypeModel getType(ClassModel classModel) {
		return new ClassTypeModel(classModel, Collections.EMPTY_LIST);
	}

	/**
	 * get the type model for a generic class given its generic list
	 *
	 * @param classModel
	 * @return
	 */
	public static ClassTypeModel getGenericType(ClassModel classModel, List<ClassTypeModel> genericList) {
		return new ClassTypeModel(classModel, genericList);
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
		return new ArrayTypeModel(getType(classModel), dimension);
	}

}
