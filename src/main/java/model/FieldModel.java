package model;

import analyzer.IVisitable;
import analyzer.IVisitor;
import generator.classParser.IFieldModel;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;
import utility.Modifier;

/**
 * Representing field in java programs
 *
 * @author zhang
 */
class FieldModel implements IVisitable<FieldModel>, IFieldModel {
    private final FieldNode asmFieldNode;
    private final ClassModel belongsTo;

    private final Modifier modifier;
    private final boolean isFinal;
    private final TypeModel fieldType;

    /**
     * creates an FieldModel given the class it belongs to, and the asmFieldNode
     *
     * @param classModel
     * @param fieldNode
     */
    public FieldModel(ClassModel classModel, FieldNode fieldNode) {
        belongsTo = classModel;
        asmFieldNode = fieldNode;
        modifier = Modifier.parse(asmFieldNode.access);
        isFinal = Modifier.parseIsFinal(asmFieldNode.access);
        fieldType = TypeModel.parse(Type.getType(asmFieldNode.desc));
    }

    public String getName() {
        return asmFieldNode.name;
    }

    public TypeModel getType() {
        return fieldType;
    }

    @Override
    public String getTypeName() {
        return fieldType.getName();
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

    public TypeModel getFieldType() {
        return fieldType;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public void visit(IVisitor<FieldModel> IVisitor) {
        IVisitor.visit(this);
    }

}
