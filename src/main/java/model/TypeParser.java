package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.objectweb.asm.Type;

/**
 * A factory method utility for type model
 * 
 * @author zhang
 *
 */
class TypeParser {

	/**
	 * convert asm's type instance to TypeModel, assume this type has empty
	 * generic list
	 *
	 * @param type
	 * @return the corresponding type model
	 */
	static TypeModel parse(Type type) {
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
	static ClassTypeModel getType(ClassModel classModel) {
		return new ClassTypeModel(classModel, Collections.EMPTY_LIST);
	}

	/**
	 * get the type model for a generic class given its generic list
	 *
	 * @param classModel
	 * @return
	 */
	static ClassTypeModel getGenericType(ClassModel classModel, List<ClassTypeModel> genericList) {
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
	static TypeModel getArrayType(ClassModel classModel, int dimension) {
		return new ArrayTypeModel(getType(classModel), dimension);
	}

	/**
	 * 
	 * @param arg
	 *            the argument description string found in class or method's
	 *            signature
	 * @return the generic type model representing this
	 */
	static GenericTypeModel parseGenericType(String arg) {
		// E:Ljava/lang/Object
		String[] sp = arg.split(":");
		String key = sp[0];
		// has a lower bound
		ClassModel bound = ASMParser.getClassByName(sp[sp.length - 1].substring(1));
		return GenericTypeModel.getLowerBounded(TypeParser.getType(bound), key);
	}

	/**
	 * 
	 * @param signature
	 *            of a class or a method
	 * @return the list of generic parameter this class or method needs
	 */
	static List<GenericTypeModel> parseGenericTypeList(String signature) {
		List<GenericTypeModel> ls = new ArrayList<>();
		if (signature != null && signature.length() >= 1 && signature.charAt(0) == '<') {
			int count = 1, i = 1, j = 1;
			while (count != 0) {
				switch (signature.charAt(j)) {
				case '<':
					count++;
					break;
				case '>':
					count--;
					break;
				case ';':
					if (count == 1) {
						ls.add(TypeParser.parseGenericType(signature.substring(i, j)));
						i = j + 1;
					}
					break;
				default:
					break;
				}
				j++;
			}
		}
		return ls;
	}

}
