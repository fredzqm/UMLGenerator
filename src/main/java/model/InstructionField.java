package model;

import org.objectweb.asm.tree.FieldInsnNode;

import analyzer.utility.IClassModel;

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

    public InstructionField(MethodModel method, FieldInsnNode fieldCall) {
        super(method);
        this.calledOn = TypeParser.parseClassInternalName(fieldCall.owner);
        ClassModel destClass = calledOn.getClassModel();
        if (destClass == null) {
            throw new RuntimeException();
        }
        this.field = destClass.getFieldByName(fieldCall.name);
        if (this.field == null) {
            Logger.logError(getClass().getName() + "::field is null: " + fieldCall.desc + "\tname\t" + fieldCall.name
                    + "\towner\t" + fieldCall.owner);
        }
    }

    @Override
    public Collection<TypeModel> getDependentTypes() {
        if (this.field == null) {
            return Collections.singletonList(calledOn);
        }
        return Arrays.asList(calledOn, field.getFieldType());
    }

    /**
     * @return the field it accesses
     */
    public FieldModel getAccessComponent() {
        return this.field;
    }

    @Override
    public IClassModel getCalledOn() {
        return calledOn.getClassModel();
    }

}
