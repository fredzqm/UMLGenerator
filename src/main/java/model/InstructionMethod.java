package model;

import java.util.Collection;
import java.util.HashSet;

import org.objectweb.asm.tree.MethodInsnNode;

public class InstructionMethod extends InstructionModel {
    private final TypeModel calledOn;
    private MethodModel method;

    public InstructionMethod(MethodModel method, MethodInsnNode methodCall) {
        super(method);
        calledOn = TypeParser.parseClassInternalName(methodCall.owner);
        ClassModel destClass = calledOn.getClassModel();
        if (destClass == null) {
            // methods called on primitive type
//            System.out.println("Primitive type");
//            System.out.println("\tdesc\t" + methodCall.desc);
//            System.out.println("\tname\t" + methodCall.name);
//            System.out.println("\towner\t" + methodCall.owner);
//            Signature sign = Signature.parse(methodCall.name, methodCall.desc);
//            System.out.println("\t\t" + sign);
            method = null;
        } else {
            Signature signature = Signature.parse(methodCall.name, methodCall.desc);
            method = destClass.getMethodBySignature(signature);
            if (method == null) {
                System.out.println("Method is null");
                System.out.println("\tdesc\t" + methodCall.desc);
                System.out.println("\tname\t" + methodCall.name);
                System.out.println("\towner\t" + methodCall.owner);
                Signature sign = Signature.parse(methodCall.name, methodCall.desc);
                System.out.println("\t\t" + sign);
                method = destClass.getMethodBySignature(signature);
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

}
