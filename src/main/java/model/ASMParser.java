package model;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ASMParser implements ASMServiceProvider {
	private Map<String, ClassModel> map = new HashMap<>();

	public ASMParser() {
		map = new HashMap<>();
	}

	@Override
	public ClassModel getClassByName(String className) {
		return getClassByName(className, false);
	}

	private ClassModel getClassByName(String className, boolean important) {
		if (map.containsKey(className))
			return map.get(className);
		try {
			ClassReader reader = new ClassReader(className);
			ClassNode classNode = new ClassNode();
			reader.accept(classNode, ClassReader.EXPAND_FRAMES);
			ClassModel model = new ClassModel(this, classNode, important);
			map.put(className, model);
			return model;
		} catch (IOException e) {
			throw new RuntimeException("ASM parsing of " + className + " failed.", e);
		}
	}

	/**
	 * 
	 * @param importClassesList
	 *            important classes for this parser
	 * @return ASMParser instance that already parsed the important classes.
	 */
	public static ASMParser getInstance(Iterable<String> importClassesList) {
		ASMParser parser = new ASMParser();
		for (String importantClass : importClassesList) {
			parser.getClassByName(importantClass, true);
		}
		return parser;
	}
}
