package model;

import java.util.Collection;
import analyzer.IASystemModel;

/**
 * 
 * This class representing the entire model of a java program
 * 
 * @author zhang
 *
 */
public class SystemModel implements IASystemModel {
	private Collection<ClassModel> classList;

	private SystemModel(Collection<ClassModel> importantList) {
		this.classList = importantList;
	}

	/**
	 * constructs an instance of SystemModel given the configuration
	 *
	 * @param config
	 * @return
	 */
	public static SystemModel getInstance(IModelConfiguration config) {
		Iterable<String> importClassesList = config.getClasses();
		if (importClassesList == null)
			throw new RuntimeException("important classes list cannot be null!");

		int recursiveFlag;
		if (config.isRecursive()) {
			recursiveFlag = ASMParser.RECURSE_INTERFACE | ASMParser.RECURSE_SUPERCLASS | ASMParser.RECURSE_HAS_A;
		} else {
			recursiveFlag = 0;
		}

		Collection<ClassModel> ls = ASMParser.getClasses(importClassesList, recursiveFlag);
		return new SystemModel(ls);
	}

	@Override
	public Collection<ClassModel> getClasses() {
		return classList;
	}

}
