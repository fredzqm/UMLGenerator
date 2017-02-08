package model;

import org.objectweb.asm.tree.FieldInsnNode;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * represent a java byte code that accesses a field
 *
 * @author zhang
 */
public class InstructionField extends InstructionModel {
    private final TypeModel calledOn;
    private final FieldModel field;

    public InstructionField(MethodModel method, FieldInsnNode fiedlCall) {
        super(method);
        calledOn = TypeParser.parseClassInternalName(fiedlCall.owner);
        ClassModel destClass = calledOn.getClassModel();
        if (destClass == null) {
            throw new RuntimeException();
        }
        field = destClass.getFieldByName(fiedlCall.name);
        if (field == null) {
            Logger.logError(getClass().getName() + "::field is null: " + fiedlCall.desc + "\tname\t" + fiedlCall.name
                    + "\towner\t" + fiedlCall.owner);
        }
    }

    @Override
    public Collection<TypeModel> getDependentTypes() {
        if (field == null) {
            return Collections.singletonList(calledOn);
        }
        return Arrays.asList(calledOn, field.getFieldType());
    }

    /**
     * @return the field it accesses
     */
    public FieldModel getAccessComponent() {
        return field;
    }

}
