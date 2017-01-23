package model;

import analyzer.utility.ClassPair;
import analyzer.utility.IRelationInfo;
import analyzer.utility.ISystemModel;
import config.IConfiguration;
import config.ModelConfiguration;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class representing the entire model of a java program
 *
 * @author zhang
 */
public class SystemModel implements ISystemModel {
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
    public static SystemModel getInstance(ModelConfiguration config) {
        Iterable<String> importClassesList = config.getClasses();
        if (importClassesList == null)
            throw new RuntimeException("important classes list cannot be null!");

        int recursiveFlag;
        if (config.isRecursive()) {
            recursiveFlag = ASMParser.RECURSE_INTERFACE | ASMParser.RECURSE_SUPERCLASS | ASMParser.RECURSE_HAS_A
                    | ASMParser.RECURSE_DEPENDS_ON;
        } else {
            recursiveFlag = 0;
        }

        Collection<ClassModel> ls = ASMParser.getClasses(importClassesList, recursiveFlag);
        return new SystemModel(ls);
    }

    public static SystemModel getInstance(IConfiguration iConfig) {
        ModelConfiguration config = iConfig.createConfiguration(ModelConfiguration.class);
        return getInstance(config);
    }

    @Override
    public Collection<ClassModel> getClasses() {
        return classList;
    }

    @Override
    public Map<ClassPair, List<IRelationInfo>> getRelations() {
        return Collections.emptyMap();
    }

}
