package model;

import org.objectweb.asm.tree.MethodInsnNode;

public class InstructionMethod extends InstructionModel {
    int dimension;
    TypeModel owner;

    public InstructionMethod(MethodModel method, MethodInsnNode methodCall) {
        super(method);
        owner = TypeParser.parseClassInternalName(methodCall.owner);
        ClassModel destClass = owner.getClassModel();
        if (destClass == null) {

        } else {
            Signature signature = Signature.parse(methodCall.name, methodCall.desc);
            MethodModel method2 = destClass.getMethodBySignature(signature);
            if (method2 == null)
                method2 = destClass.getMethodBySignature(signature);
        }
        // System.out.println("\tdesc\t" + methodCall.desc);
        // System.out.println("\tname\t" + methodCall.name);
        // System.out.println("\towner\t" + methodCall.owner);
        // Signature sign = Signature.parse(methodCall.name, methodCall.desc);
        // System.out.println("\t\t" + sign);
    }

}
