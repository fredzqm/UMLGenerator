package model;

import java.util.Collection;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

import analyzer.IInstructionModel;

/**
 * Represent a java byte code instruction
 * 
 * @author zhang
 *
 */
public abstract class InstructionModel implements IInstructionModel {
    private final MethodModel belongTo;

    public InstructionModel(MethodModel belongTo) {
        this.belongTo = belongTo;
    }

    public MethodModel getBelongTo() {
        return belongTo;
    }

    public abstract Collection<TypeModel> getDependentClass();

    public static InstructionModel parseInstruction(MethodModel method, AbstractInsnNode insn) {
        switch (insn.getType()) {
            case AbstractInsnNode.METHOD_INSN:
                MethodInsnNode methodCall = (MethodInsnNode) insn;
                return new InstructionMethod(method, methodCall);
            case AbstractInsnNode.FIELD_INSN:
                FieldInsnNode fiedlCall = (FieldInsnNode) insn;
                return new InstructionField(method, fiedlCall);
            default:
                return null;
        }
    }

}
