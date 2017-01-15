package model;

import java.util.Collections;
import java.util.List;

/**
 * representing any primitive type
 *
 * @author zhang
 */
class PrimitiveType extends TypeModel {
    public static PrimitiveType INT = new PrimitiveType("int");
    public static PrimitiveType DOUBLE = new PrimitiveType("double");
    public static PrimitiveType FLOAT = new PrimitiveType("float");
    public static PrimitiveType BOOLEAN = new PrimitiveType("boolean");
    public static PrimitiveType BYTE = new PrimitiveType("byte");
    public static PrimitiveType CHAR = new PrimitiveType("char");
    public static PrimitiveType SHORT = new PrimitiveType("short");
    public static PrimitiveType LONG = new PrimitiveType("long");
    public static PrimitiveType VOID = new PrimitiveType("void");

    private final String name;

    PrimitiveType(String name) {
        this.name = name;
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
            case 'V':
                return VOID;
            default:
                throw new RuntimeException(x + " Cannot represent a primitive type");
        }
    }

    public String getName() {
        return name;
    }

    public ClassModel getClassModel() {
        return null;
    }

    public Iterable<TypeModel> getSuperTypes() {
        return Collections.emptyList();
    }

    public List<ClassModel> getDependentClass() {
        return Collections.emptyList();
    }

    public String toString() {
        return getName();
    }
}
