package model;

import generator.ISystemModel;

import java.util.Collection;

/**
 * This class representing the entire model of a java program
 */
public class SystemModel implements ISystemModel {
    private Iterable<ClassModel> importantList;

    private SystemModel(Iterable<ClassModel> importantList) {
        this.importantList = importantList;
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
			recursiveFlag = ASMParser.RECURSE_INTERFACE | ASMParser.RECURSE_SUPERCLASS;
		} else {
			recursiveFlag = 0;
		}
		
        Collection<ClassModel> ls = ASMParser.getClasses(importClassesList, recursiveFlag);
        return new SystemModel(ls);
    }
    
    @Override
    public Iterable<ClassModel> getClasses() {
        return importantList;
    }

}
