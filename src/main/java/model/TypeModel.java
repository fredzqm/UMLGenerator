package model;

import org.objectweb.asm.Type;

/**
 * Representing the type in java programs
 *
 * @author zhang
 */
class TypeModel {
    private final ClassModel classModel;
    private final int dimension;
    private final PrimitiveType primiType;

    private TypeModel(ClassModel classModel, int dimension, PrimitiveType primiType) {
        this.classModel = classModel;
        this.dimension = dimension;
        this.primiType = primiType;
    }

    /**
     * convert asm's type instance to TypeModel
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
        PrimitiveType primiType = PrimitiveType.parse(type);
        ClassModel classModel;
        if (primiType == PrimitiveType.OBJECT)
            classModel = ASMParser.getClassByName(type.getClassName());
        else
            classModel = null;
        return new TypeModel(classModel, dimension, primiType);
    }

    /**
     * get the type model for a specific class
     *
     * @param classModel
     * @return
     */
    public static TypeModel getInstance(ClassModel classModel) {
        return getInstance(classModel, 0);
    }

    /**
     * get the array type model for a specific class
     *
     * @param classModel
     * @param dimension  the dimension of the array
     * @return
     */
    public static TypeModel getInstance(ClassModel classModel, int dimension) {
        return new TypeModel(classModel, dimension, PrimitiveType.OBJECT);
    }

    /**
     * @return the class model behind this type model. null if it is a primitive
     * type
     */
    public ClassModel getClassModel() {
        return classModel;
    }

    public int getDimension() {
        return dimension;
    }

    public String getName() {
        StringBuilder sb = new StringBuilder();
        if (classModel != null)
            sb.append(classModel.getName());
        else
            sb.append(primiType.getName());
        for (int i = 0; i < dimension; i++) {
            sb.append("[]");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TypeModel) {
            TypeModel o = (TypeModel) obj;
            return classModel == o.classModel && primiType == o.primiType && dimension == o.dimension;
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (classModel == null)
            return primiType.hashCode() + dimension;
        return classModel.hashCode() + primiType.hashCode() + dimension;
    }

    @Override
    public String toString() {
        return getName();
    }

    private enum PrimitiveType {
        INT, DOUBLE, FLOAT, BOOLEAN, BYTE, CHAR, SHORT, LONG, OBJECT, VOID;

        public static PrimitiveType parse(Type type) {
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
