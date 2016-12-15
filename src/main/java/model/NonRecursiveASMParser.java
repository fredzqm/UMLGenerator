package model;

import java.util.HashSet;
import java.util.Set;

public class NonRecursiveASMParser extends AbstractASMParser {
	Set<String> importantLs = new HashSet<>();;

	/**
	 * 
	 * @param importantList
	 *            important classes for this parser
	 * @return ASMParser instance that already parsed the important classes.
	 */
	NonRecursiveASMParser(Iterable<String> importantList) {
		super();
		importantList.forEach((s) -> {
			importantLs.add(s.replace(".", "/"));
			importantLs.add(s.replace("/", "."));
		});
		addImportantClasses(importantList);
	}

	@Override
	public ClassModel getClassByName(String name) {
		if (importantLs.contains(name))
			return parseClass(name);
		return null;
	}

}
