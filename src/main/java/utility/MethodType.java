package utility;

import org.objectweb.asm.Opcodes;

public enum MethodType {
    CONSTRUCTOR, STATIC_INITIALIZER, METHOD, STATIC, ABSTRACT;

    public static MethodType parse(String name, int access) {
        if (Modifier.parseIsStatic(access)) {
            if (name.equals("<clinit>"))
                return STATIC_INITIALIZER;
            return STATIC;
        } else {
            if ((access & Opcodes.ACC_ABSTRACT) != 0)
                return ABSTRACT;
            if (name.equals("<init>"))
                return CONSTRUCTOR;
            return METHOD;
        }
    }

}