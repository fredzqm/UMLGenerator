package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	 * 
	 * @param internalName
	 *            the internal name representing a type of a class
	 * @return the corresponding class type model
	 */
	static TypeModel parseTypeSignature(String internalName) {
		char x = internalName.charAt(0);
		if (internalName.length() == 1) {
			return PrimitiveType.parseTypeModel(x);
		}
		return parseFieldTypeSignature(internalName);
	}

	static TypeModel parseFieldTypeSignature(String internalName) {
		char x = internalName.charAt(0);
		switch (x) {
		case 'L':
			return parseClassTypeSignature(internalName);
		case 'T':
			return new GenericTypeVar(internalName.substring(1));
		case '[':
			int dim = 1;
			while (dim < internalName.length()) {
				if (internalName.charAt(dim) != '[') {
					return new ArrayTypeModel(parseTypeSignature(internalName.substring(dim)), dim);
				}
				dim++;
			}
		default:
			throw new RuntimeException(internalName + " is not a valid field type signature");
		}
	}

	static TypeModel parseClassTypeSignature(String internalName) {
		int index = internalName.indexOf('<');
		if (index < 0) {
			return ASMParser.getClassByName(internalName.substring(1));
		} else {
			ClassModel bound = ASMParser.getClassByName(internalName.substring(1, index));
			List<TypeModel> genericEnv = parseTypeArgs(internalName.substring(index));
			return new ParametizedClassModel(bound, genericEnv);
		}
	}

	/**
	 * 
	 * @param internalName
	 *            the internal name representing a type of a class
	 * @return the corresponding class type model
	 */
	static TypeModel parseTypeArg(String internalName) {
		char x = internalName.charAt(0);
		if (x == '*') {
			return GenericTypeModel.getWildType();
		} else if (x == '+') {
			return GenericTypeModel.getLowerBounded(parseFieldTypeSignature(internalName.substring(1)));
		} else if (x == '-') {
			return GenericTypeModel.getLowerBounded(parseFieldTypeSignature(internalName.substring(1)));
		} else {
			return parseFieldTypeSignature(internalName);
		}
	}

	static List<TypeModel> parseTypeArgs(String paraLs) {
		if (paraLs.charAt(0) != '<' || paraLs.charAt(paraLs.length() - 1) != '>')
			throw new RuntimeException(paraLs + " is not a valid parameter list");
		List<TypeModel> ret = new ArrayList<>();
		for (String s : splitOn(paraLs.substring(1, paraLs.length() - 1))) {
			ret.add(parseTypeArg(s));
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
	static GenericTypeModel parseTypeParam(String arg) {
		String[] sp = arg.split(":");
		String key = sp[0];
		TypeModel type = (TypeModel) parseTypeArg(sp[sp.length - 1]);
		return GenericTypeModel.getLowerBounded(type);
	}

	static List<GenericTypeParams> parseTypeParams(String paramList) {
		List<GenericTypeParams> ret = new ArrayList<>();
		
		return ret;
	}

	/**
	 * 
	 * @param signature
	 *            of a class or a method
	 * @return the list of generic parameter this class or method needs
	 */
	static ClassSignatureParseResult parseClassSignature(String signature) {
		List<GenericTypeParams> typeParameters;
		int i = 0;
		if (signature.charAt(0) != '<') {
			typeParameters = Collections.EMPTY_LIST;
		} else {
			i = indexAfterClosing(signature, 0);
			typeParameters = parseTypeParams(signature.substring(0, i));
		}
		List<TypeModel> superTypes = new ArrayList<>();
		for (String s : splitOn(signature.substring(i))) {
			superTypes.add(parseClassTypeSignature(s));
		}
		return new ClassSignatureParseResult(typeParameters, superTypes);
	}

	private static Iterable<String> splitOn(String str) {
		List<String> ls = new ArrayList<>();
		int i = 0, start = 0, c = 0;
		while (i < str.length()) {
			char x = str.charAt(i++);
			if (x == '<') {
				c++;
			} else if (x == '>') {
				c--;
			} else if (x == ';' && c == 0) {
				ls.add(str.substring(start, i - 1));
				start = i;
			}
		}
		return ls;
	}

	private static int indexAfterClosing(CharSequence signature, int i) {
		if (signature.charAt(i) != '<')
			return i;
		int c = 1;
		i++;
		while (i < signature.length()) {
			char x = signature.charAt(i++);
			if (x == '<') {
				c++;
			} else if (x == '>') {
				c--;
				if (c == 0)
					return i;
			}
		}
		throw new RuntimeException("bracket is not closed");
	}

	public static class ClassSignatureParseResult {
		private List<GenericTypeParams> typeParameters;
		private List<TypeModel> superTypes;

		public ClassSignatureParseResult(List<GenericTypeParams> typeParameters, List<TypeModel> superTypes) {
			this.typeParameters = typeParameters;
			this.superTypes = superTypes;
		}

		public List<GenericTypeParams> getParamsList() {
			return typeParameters;
		}

		public List<TypeModel> getSuperTypes() {
			return superTypes;
		}
	}

}
