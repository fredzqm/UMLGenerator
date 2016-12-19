package model;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The concrete ASM service provider that will recursively parse all related
 * classes request. {@see NonRecursiveASMParser}
 *
 * @author zhang
 */
public class ASMParser implements ASMClassTracker {
	public static int AUTO_CREATE_OFF = 0x1;
	public static int RECURSE_SUPERCLASS = 0x2;
	public static int RECURSE_INTERFACE = 0x4;
	public static int RECURSE_FILEDS = 0x8;

	final private int recursiveFlag;

	private Map<String, ClassModel> map;

	/**
	 * create an ASM parser with a certain recursive factor
	 */
	private ASMParser(int recursiveFlag) {
		this.recursiveFlag = recursiveFlag;
		map = new HashMap<>();
	}

	/**
	 * create an non-recursive ASM parser by default
	 */
	public ASMParser() {
		this(0);
	}

	/**
	 * creates an instance of ASMParsre given the configuration
	 * 
	 * @param config
	 * @return
	 */
	public static ASMParser getInstance(IModelConfiguration config) {
		int recurisveFlag = 0;
		if (config.isRecursive()) {
			recurisveFlag = 0x6;
		} else {
			recurisveFlag |= AUTO_CREATE_OFF;
		}
		ASMParser parser = new ASMParser(recurisveFlag);
		parser.addClasses(config.getClasses());
		return parser;
	}

	@Override
	public ClassModel getClassByName(String className) {
		className = className.replace(".", "/");
		if ((recursiveFlag & AUTO_CREATE_OFF) != 0 && !map.containsKey(className)) {
			return null;
		}
		return getClassExplicity(className);
	}

	private ClassModel getClassExplicity(String className) {
		ClassModel model = parseClass(className);
		if ((recursiveFlag & RECURSE_SUPERCLASS) != 0)
			model.getSuperClass();
		if ((recursiveFlag & RECURSE_INTERFACE) != 0)
			model.getInterfaces();
		if ((recursiveFlag & RECURSE_FILEDS) != 0)
			model.getFields();
		return model;
	}

	private ClassModel parseClass(String className) {
		if (map.containsKey(className))
			return map.get(className);
		try {
			ClassReader reader = new ClassReader(className);
			ClassNode classNode = new ClassNode();
			reader.accept(classNode, ClassReader.EXPAND_FRAMES);
			ClassModel model = new ClassModel(this, classNode);
			map.put(className, model);
			return model;
		} catch (IOException e) {
			throw new RuntimeException("ASM parsing of " + className + " failed.", e);
		}
	}

	public void addClasses(Iterable<String> importClassesList) {
		if (importClassesList != null) {
			for (String importantClass : importClassesList) {
				getClassExplicity(importantClass.replace(".", "/"));
			}
		}
	}

	/**
	 * Not that this method returns an Iterable. If you add more classes to
	 * ASMParser, the old list will be invalid
	 * <p>
	 * Therefore, you should always wrap it in another list or set
	 */
	public Collection<ClassModel> getClasses() {
		return map.values();
	}

	@Override
	public String toString() {
		return map.toString();
	}

}
