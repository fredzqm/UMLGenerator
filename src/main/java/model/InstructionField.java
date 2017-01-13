package model;

import java.util.Collection;

import org.objectweb.asm.tree.FieldInsnNode;

public class InstructionField extends InstructionModel {

    public InstructionField(MethodModel method, FieldInsnNode fiedlCall) {
        super(method);
//        TypeModel type = TypeParser.parse(Type.getObjectType(fiedlCall.owner));
//        ClassModel destClass = ASMParser.getClassByName(type.getName());
//        if (destClass == null)
//            continue;
//        FieldModel field = destClass.getFieldByName(fiedlCall.name);
//        if (field == null)
//            continue;
    }

    @Override
    public Collection<TypeModel> getDependentClass() {
        return null;
    }

}
