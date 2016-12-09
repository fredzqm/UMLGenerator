package main.model;

import org.objectweb.asm.Opcodes;

public enum Modifier {
	PUBLIC, PRIVATE, PROTECTED, DEFAULT;

	public static Modifier parse(int access) {
		if ((access & Opcodes.ACC_PUBLIC) != 0)
			return PUBLIC;
		if ((access & Opcodes.ACC_PRIVATE) != 0)
			return PRIVATE;
		if ((access & Opcodes.ACC_PROTECTED) != 0)
			return PROTECTED;
		return DEFAULT;
	}
}
