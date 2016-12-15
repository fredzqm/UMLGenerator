package model;

/**
 * The concrete ASM service provider that will recursively parse all related
 * classes request. {@see NonRecursiveASMParser}
 * 
 * @author zhang
 *
 */
public class ASMParser extends AbstractASMParser {

	/**
	 * 
	 * @param importantList
	 *            important classes for this parser
	 * @return ASMParser instance that already parsed the important classes.
	 */
	public ASMParser(Iterable<String> importantList) {
		super();
		addImportantClasses(importantList);
	}

	/**
	 * create an ASM parser with empty important class list.
	 */
	public ASMParser() {
		this(null);
	}

	@Override
	public ClassModel getClassByName(String className) {
		ClassModel model = parseClass(className);
		model.getSuperClass();
		return model;
	}

}
