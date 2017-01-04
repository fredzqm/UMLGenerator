package model;

import java.util.ArrayList;
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
			return classModel;
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
	 * get the array type model for a specific class
	 *
	 * @param classModel
	 * @param dimension
	 *            the dimension of the array
	 * @return
	 */
	static TypeModel getArrayType(TypeModel classModel, int dimension) {
		return new ArrayTypeModel(classModel, dimension);
	}

	/**
	 * 
	 * @param internalName
	 *            the internal name representing a type of a class
	 * @return the corresponding class type model
	 */
	static ClassTypeModel parseClassTypeModel(String internalName) {
		char x = internalName.charAt(0);
		if (x == 'T') {
			GenericTypePlaceHolder type = new GenericTypePlaceHolder(internalName.substring(1));
			return type;
		} else if (x == 'L') {
			int index = internalName.indexOf('<');
			if (index < 0) {
				return ASMParser.getClassByName(internalName.substring(1));
			} else {
				ClassModel bound = ASMParser.getClassByName(internalName.substring(1, index));
				List<ClassTypeModel> genericEnv = parseParameterList(internalName.substring(index));
				return new ParametizedClassModel(bound, genericEnv);
			}
		}
		throw new RuntimeException(internalName + " does not represent a class type");
	}

	static List<ClassTypeModel> parseParameterList(String parameterList) {
		if (parameterList.charAt(0) != '<' || parameterList.charAt(parameterList.length() - 1) != '>')
			throw new RuntimeException(parameterList + " is not a valid parameter list");
		String[] sp = parameterList.substring(1, parameterList.length() - 1).split(";");
		List<ClassTypeModel> ret = new ArrayList<>();
		for (String s : sp) {
			ret.add(parseClassTypeModel(s));
		}
		return ret;
	}

	/**
	 * 
	 * @param arg
	 *            the argument description string found in class or method's
	 *            signature
	 * @return the generic type model representing this
	 */
	static GenericTypeModel parseGenericType(String arg) {
		String[] sp = arg.split(":");
		String key = sp[0];
		ClassTypeModel type = (ClassTypeModel) parseClassTypeModel(sp[sp.length - 1]);
		return GenericTypeModel.getLowerBounded(type, key);
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
