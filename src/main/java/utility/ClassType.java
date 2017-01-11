package utility;

import org.objectweb.asm.Opcodes;

public enum ClassType {
    ABSTRACT, INTERFACE, CONCRETE, ENUM;

    public static ClassType parse(int access) {
        if ((access & Opcodes.ACC_ENUM) != 0)
            return ClassType.ENUM;
        if ((access & Opcodes.ACC_INTERFACE) != 0)
            return ClassType.INTERFACE;
        if ((access & Opcodes.ACC_ABSTRACT) != 0)
            return ClassType.ABSTRACT;
        return CONCRETE;
    }
}