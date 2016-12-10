package models;

import org.objectweb.asm.Opcodes;

public enum Modifier {
	PUBLIC("+"), DEFAULT(""), PROTECTED("#"), PRIVATE("-");

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

	private final String modifierValue;

	Modifier(String modifierValue) {
		this.modifierValue = modifierValue;
	}

	public String getModifierValue() {
		return this.modifierValue;
	}
}
