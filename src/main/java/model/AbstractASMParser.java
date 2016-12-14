package model;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The concrete ASM service provider that will recursively parse all related
 * classes request. {@see NonRecursiveASMParser}
 * 
 * @author zhang
 *
 */
public abstract class AbstractASMParser implements ASMServiceProvider {
	private Map<String, ClassModel> map = new HashMap<>();

	public AbstractASMParser(Iterable<String> importClassesList) {
		map = new HashMap<>();
		for (String importantClass : importClassesList) {
			getClassByName(importantClass, true);
		}
	}

	protected ClassModel getClassByName(String className, boolean important) {
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

	protected boolean hasAlreadyParse(String className) {
		return map.containsKey(className);
	}

}
