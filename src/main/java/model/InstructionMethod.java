package model;

import java.util.Collection;
import java.util.HashSet;

import org.objectweb.asm.tree.MethodInsnNode;

public class InstructionMethod extends InstructionModel {
    private final TypeModel calledOn;
    private final MethodModel method;

    public InstructionMethod(MethodModel belongTo, MethodInsnNode methodCall) {
        super(belongTo);
        this.calledOn = TypeParser.parseClassInternalName(methodCall.owner);
        ClassModel destClass = calledOn.getClassModel();
        if (destClass == null) {
            method = null;
        } else {
            Signature signature = Signature.parse(methodCall.name, methodCall.desc);
            method = destClass.getMethodBySignature(signature);
            if (belongTo == null) {
                System.err.println(getClass().getName() + "Method is null \tdesc\t" + methodCall.desc + "\tname\t"
                        + methodCall.name + "\towner\t" + methodCall.owner);
            }
        }
    }

    @Override
    public Collection<TypeModel> getDependentClass() {
        Collection<TypeModel> depends = new HashSet<>();
        depends.add(calledOn);
        if (method != null) {
            depends.addAll(method.getArguments());
            depends.add(method.getReturnType());
        }
        return depends;
    }

    public MethodModel getMethod() {
        return method;
    }
}
