package main.java.model;

import org.objectweb.asm.Opcodes;

import main.java.generator.IModifier;

public enum Modifier implements IModifier {
    PUBLIC("+"), DEFAULT(""), PROTECTED("#"), PRIVATE("-");

    private final String modifierValue;

    Modifier(String modifierValue) {
        this.modifierValue = modifierValue;
    }

    public static Modifier parse(int access) {
        if ((access & Opcodes.ACC_PUBLIC) != 0)
            return PUBLIC;
        if ((access & Opcodes.ACC_PRIVATE) != 0)
            return PRIVATE;
        if ((access & Opcodes.ACC_PROTECTED) != 0)
            return PROTECTED;
        return DEFAULT;
    }

    public static boolean parseIsFinal(int access) {
        return (access & Opcodes.ACC_FINAL) != 0;
    }

    public String getModifierSymbol() {
        return modifierValue;
    }

    @Override
    public void switchByCase(Switcher switcher) {
        switch (this) {
            case PRIVATE:
                switcher.ifPrivate();
                break;
            case PUBLIC:
                switcher.ifPublic();
                break;
            case PROTECTED:
                switcher.ifProtected();
                break;
            case DEFAULT:
                switcher.ifDefault();
                break;
        }
    }
}
