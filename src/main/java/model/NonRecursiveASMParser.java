package model;

import java.util.HashSet;
import java.util.Set;

public class NonRecursiveASMParser extends AbstractASMParser {
	Set<String> importantLs;

	/**
	 * 
	 * @param importantClassesList
	 *            important classes for this parser
	 * @return ASMParser instance that already parsed the important classes.
	 */
	NonRecursiveASMParser(Iterable<String> importantClassesList) {
		super(importantClassesList);
		importantLs = new HashSet<>();
		importantClassesList.forEach((s) -> {
			importantLs.add(s.replace(".", "/"));
			importantLs.add(s.replace("/", "."));
		});
	}

	@Override
	public ClassModel getClassByName(String name) {
		if (importantLs.contains(name))
			return getClassByName(name, false);
		return null;
	}

}
