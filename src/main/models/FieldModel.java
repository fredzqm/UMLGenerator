package models;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;

public class FieldModel implements Visitable<FieldModel> {
	private final FieldNode asmFieldNode;
	private final ClassModel belongsTo;

	private final Modifier modifier;
	private final boolean isFinal;
	private final TypeModel fieldType;

	public FieldModel(ClassModel classModel, FieldNode fieldNode) {
		belongsTo = classModel;
		asmFieldNode = fieldNode;
		modifier = Modifier.parse(asmFieldNode.access);
		isFinal = Modifier.parseIsFinal(asmFieldNode.access);
		fieldType = TypeModel.parse(classModel, Type.getType(asmFieldNode.desc));
	}

	public String getName() {
		return asmFieldNode.name;
	}

	public ClassModel getParentClass() {
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
	public void visit(Visitor<FieldModel> visitor) {
		visitor.visit(this);
	}

}
