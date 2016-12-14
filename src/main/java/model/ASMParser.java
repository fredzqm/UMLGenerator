package model;

import java.util.ArrayList;

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
	 * @param importClassesList
	 *            important classes for this parser
	 * @return ASMParser instance that already parsed the important classes.
	 */
	public ASMParser(Iterable<String> importClassesList) {
		super(importClassesList);
	}

	public ASMParser() {
		this(new ArrayList<>());
	}

	@Override
	public ClassModel getClassByName(String className) {
		return getClassByName(className, false);
	}

}
