package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.objectweb.asm.Type;

/**
 * A factory method utility for type model. It basically parses the signature
 * according to jVM's definition. Each method corresponds to the a context free
 * grammar in JVM's specification
 * <p>
 * Check Java Virtual Machine Specification for more details.
 *
 * @author zhang
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
     * @param typeSig
     *            the internal name representing a type of a class
     * @return the corresponding class type model
     */
    static TypeModel parseTypeSignature(String typeSig) {
        char x = typeSig.charAt(0);
        if (typeSig.length() == 1) {
            return PrimitiveType.parseTypeModel(x);
        }
        return parseFieldTypeSignature(typeSig);
    }

    static TypeModel parseFieldTypeSignature(String filedTypeSig) {
        char x = filedTypeSig.charAt(0);
        switch (x) {
            case 'L':
                return parseClassTypeSignature(filedTypeSig);
            case 'T':
                return new GenericTypeVarPlaceHolder(filedTypeSig.substring(1, filedTypeSig.length() - 1));
            case '[':
                int dim = 1;
                while (dim < filedTypeSig.length()) {
                    if (filedTypeSig.charAt(dim) != '[') {
                        return new ArrayTypeModel(parseTypeSignature(filedTypeSig.substring(dim)), dim);
                    }
                    dim++;
                }
            default:
                throw new RuntimeException(filedTypeSig + " is not a valid field type signature");
        }
    }

    static TypeModel parseClassTypeSignature(String classTypeSig) {
        if (classTypeSig.charAt(classTypeSig.length() - 1) != ';') {
            throw new RuntimeException("class type signature should end with ;   : " + classTypeSig);
        }
        int index = classTypeSig.indexOf('<');
        if (index < 0) {
            return ASMParser.getClassByName(classTypeSig.substring(1, classTypeSig.length() - 1));
        } else {
            ClassModel bound = ASMParser.getClassByName(classTypeSig.substring(1, index));
            List<TypeModel> genericEnv = parseTypeArgs(classTypeSig.substring(index, classTypeSig.length() - 1));
            return new ParametizedClassModel(bound, genericEnv);
        }
    }

    /**
     * @param typeArg
     *            the internal name representing a type of a class
     * @return the corresponding class type model
     */
    static TypeModel parseTypeArg(String typeArg) {
        char x = typeArg.charAt(0);
        if (x == '*') {
            return GenericTypeArg.getWildType();
        } else if (x == '+') {
            return GenericTypeArg.getLowerBounded(parseFieldTypeSignature(typeArg.substring(1)));
        } else if (x == '-') {
            return GenericTypeArg.getLowerBounded(parseFieldTypeSignature(typeArg.substring(1)));
        } else {
            return parseFieldTypeSignature(typeArg);
        }
    }

    static List<TypeModel> parseTypeArgs(String argLs) {
        if (argLs.charAt(0) != '<' || argLs.charAt(argLs.length() - 1) != '>')
            throw new RuntimeException(argLs + " is not a valid argument list");
        List<TypeModel> ret = new ArrayList<>();
        for (String s : splitOn(argLs.substring(1, argLs.length() - 1), (c) -> c == ';' || c == '*')) {
            ret.add(parseTypeArg(s));
        }
        return ret;
    }

    static GenericTypeParam parseTypeParam(String param) {
        String[] sp = param.split(":");
        String key = sp[0];
        List<TypeModel> ls = new ArrayList<>();
        for (int i = 1; i < sp.length; i++) {
            if (sp[i].equals(""))
                continue;
            TypeModel c = parseClassTypeSignature(sp[i]);
            if (c == ASMParser.getObject())
                continue;
            ls.add(c);
        }
        return new GenericTypeParam(key, ls);
    }

    static List<GenericTypeParam> parseTypeParams(String paramList) {
        if (paramList.charAt(0) != '<' || paramList.charAt(paramList.length() - 1) != '>')
            throw new RuntimeException(paramList + " is not a valid parameter list");
        List<GenericTypeParam> ret = new ArrayList<>();
        for (String s : splitOn(paramList.substring(1, paramList.length() - 1), (c) -> c == ';')) {
            ret.add(parseTypeParam(s));
        }
        return ret;
    }

    /**
     * @param classSig
     *            of a class or a method
     * @return the list of generic parameter this class or method needs
     */
    static ClassSignatureParseResult parseClassSignature(String classSig) {
        List<GenericTypeParam> typeParameters;
        int i = 0;
        if (classSig.charAt(0) != '<') {
            typeParameters = Collections.emptyList();
        } else {
            i = indexAfterClosing(classSig, 0);
            typeParameters = parseTypeParams(classSig.substring(0, i));
        }
        List<TypeModel> superTypes = new ArrayList<>();
        for (String s : splitOn(classSig.substring(i), (c) -> c == ';')) {
            superTypes.add(parseClassTypeSignature(s));
        }
        return new ClassSignatureParseResult(typeParameters, superTypes);
    }

    static MethodSignatureParseResult parseMethodSignature(String methodSig) {
        List<GenericTypeParam> typeParameters;
        int i = 0;
        if (methodSig.charAt(0) != '<') {
            typeParameters = Collections.emptyList();
        } else {
            i = indexAfterClosing(methodSig, 0);
            typeParameters = parseTypeParams(methodSig.substring(0, i));
        }
        if (methodSig.charAt(i) != '(')
            throw new RuntimeException("Cannot find the start of parameter list at " + i + " of " + methodSig);
        int j = methodSig.indexOf(')');
        if (j < 0)
            throw new RuntimeException("Cannot find the end of parameter list of " + methodSig);
        List<TypeModel> argumentList = new ArrayList<>();
        for (String s : splitOn(methodSig.substring(i + 1, j), new Predicate<Character>() {
            private boolean start = true;

            @Override
            public boolean test(Character c) {
                if (start) {
                    if (c == 'Z' || c == 'C' || c == 'B' || c == 'S' || c == 'I' || c == 'F' || c == 'J' || c == 'D') {
                        return true;
                    }
                    if (c != '[')
                        start = false;
                    return false;
                } else {
                    if (c == ';') {
                        start = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        })) {
            argumentList.add(parseTypeSignature(s));
        }
        String[] sp = methodSig.substring(j + 1).split("\\^");
        TypeModel returnType = parseTypeSignature(sp[0]);
        List<TypeModel> exceptionList;
        if (sp.length == 1) {
            exceptionList = Collections.emptyList();
        } else {
            exceptionList = new ArrayList<>();
            for (i = 1; i < sp.length; i++) {
                exceptionList.add(parseFieldTypeSignature(sp[i]));
            }
        }
        return new MethodSignatureParseResult(typeParameters, returnType, argumentList, exceptionList);

    }

    private static Iterable<String> splitOn(String str, Predicate<Character> stop) {
        List<String> ls = new ArrayList<>();
        int i = 0, start = 0, c = 0;
        while (i < str.length()) {
            char x = str.charAt(i++);
            if (x == '<') {
                c++;
            } else if (x == '>') {
                c--;
            } else if (c == 0 && stop.test(x)) {
                ls.add(str.substring(start, i));
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
        private List<GenericTypeParam> typeParameters;
        private List<TypeModel> superTypes;

        public ClassSignatureParseResult(List<GenericTypeParam> typeParameters, List<TypeModel> superTypes) {
            this.typeParameters = typeParameters;
            this.superTypes = superTypes;
        }

        public List<GenericTypeParam> getParamsList() {
            return typeParameters;
        }

        public List<TypeModel> getSuperTypes() {
            return superTypes;
        }
    }

    public static class MethodSignatureParseResult {
        private List<GenericTypeParam> typeParameters;
        private TypeModel returnType;
        private List<TypeModel> argumentsList;
        private List<TypeModel> exceptionList;

        public MethodSignatureParseResult(List<GenericTypeParam> typeParameters, TypeModel returnType,
                List<TypeModel> argumentList, List<TypeModel> exceptionList) {
            this.typeParameters = typeParameters;
            this.returnType = returnType;
            this.argumentsList = argumentList;
            this.exceptionList = exceptionList;
        }

        public List<GenericTypeParam> getTypeParameters() {
            return typeParameters;
        }

        public TypeModel getReturnType() {
            return returnType;
        }

        public List<TypeModel> getArguments() {
            return argumentsList;
        }

        public Signature getSignature(String name) {
            return new Signature(argumentsList, name);
        }

        public List<TypeModel> getExceptionList() {
            return exceptionList;
        }

    }

}
