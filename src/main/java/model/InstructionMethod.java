package model;

import org.objectweb.asm.tree.MethodInsnNode;

public class InstructionMethod extends InstructionModel {

    public InstructionMethod(MethodModel method, MethodInsnNode methodCall) {
        super(method);
        System.out.println(method);
        System.out.println("\tdesc\t" + methodCall.desc);
        System.out.println("\tname\t" + methodCall.name);
        System.out.println("\towner\t" + methodCall.owner);
        Signature sign = Signature.parse(methodCall.name, methodCall.desc);
        if (sign == null) {
            throw new RuntimeException();
        }
        System.out.println("\t\t"+sign);
        // TypeModel type =
        // TypeParser.parse(Type.getObjectType(methodCall.owner));
        // ClassModel destClass = ASMParser.getClassByName(type.getName());
        // if (destClass == null)
        // continue;
        // Signature signature = Signature.parse(methodCall.name,
        // methodCall.desc);
        // MethodModel method = destClass.getMethodBySignature(signature);
        // if (method == null)
        // continue;
    }

}
