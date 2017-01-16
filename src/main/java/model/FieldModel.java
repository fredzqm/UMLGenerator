package model;

import analyzer.utility.IFieldModel;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;
import utility.Modifier;

/**
 * Representing field in java programs
 *
 * @author zhang
 */
class FieldModel implements IFieldModel {
    private final FieldNode asmFieldNode;
    private final ClassModel belongsTo;

    private final Modifier modifier;
    private final boolean isFinal;
    private final TypeModel fieldType;
    private final boolean isStatic;

    /**
     * creates an FieldModel given the class it belongs to, and the asmFieldNode
     *
     * @param classModel
     * @param fieldNode
     */
    FieldModel(ClassModel classModel, FieldNode fieldNode) {
        belongsTo = classModel;
        asmFieldNode = fieldNode;
        int access = asmFieldNode.access;
        modifier = Modifier.parse(access);
        isFinal = Modifier.parseIsFinal(access);
        isStatic = Modifier.parseIsStatic(access);
        if (asmFieldNode.signature != null) {
            TypeModel rawfieldType = TypeParser.parseFieldTypeSignature(asmFieldNode.signature);
            fieldType = rawfieldType.replaceTypeVar(belongsTo.getParamsMap());
        } else {
            fieldType = TypeParser.parse(Type.getType(asmFieldNode.desc));
        }
    }

    public String getName() {
        return asmFieldNode.name;
    }

    public ClassModel getBelongTo() {
        return belongsTo;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public TypeModel getFieldType() {
        return fieldType;
    }

    @Override
    public String toString() {
        return getFieldType().toString() + getName();
    }

}
