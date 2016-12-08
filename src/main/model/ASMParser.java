package main.model;

import java.io.IOException;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

public class ASMParser implements ASMServiceProvider {
	private Map<String, ClassModel> map;

	@Override
	public TypeModel getTypeByName(String name) {
		// TODO:
		// PrimitiveType primitive = PrimitiveType.parse(name);
		// if (primitive != null)
		// return primitive;
		ClassModel model = getClassByName(name, false);
		return new TypeModel(model, 0);
	}

	@Override
	public ClassModel getClassByName(String className) {
		return getClassByName(className, false);
	}

	public ClassModel getImportantClassByName(String className) {
		return getClassByName(className, true);
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
			model.tranceInheritanceChain();
			return model;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("ASM parsing of " + className + " failed.", e);
		}
	}

}
