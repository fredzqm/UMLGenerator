package model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import analyzer.utility.ClassPair;
import analyzer.utility.IRelationInfo;
import analyzer.utility.ISystemModel;
import config.IConfiguration;

/**
 * This class representing the entire model of a java program
 *
 * @author zhang
 */
public class SystemModel implements ISystemModel {
    private Set<ClassModel> classSet;

    private SystemModel(Set<ClassModel> importantList) {
        this.classSet = importantList;
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

        Collection<String> blackList = config.getBlackList();
        Set<ClassModel> ls = ASMParser.getClasses(importClassesList, blackList, recursiveFlag);

        Logger.setVerbose(config.isVerbose());

        return new SystemModel(ls);
    }

    public static SystemModel getInstance(IConfiguration iConfig) {
        ModelConfiguration config = iConfig.createConfiguration(ModelConfiguration.class);
        return getInstance(config);
    }

    @Override
    public Set<ClassModel> getClasses() {
        return classSet;
    }

    @Override
    public Map<ClassPair, List<IRelationInfo>> getRelations() {
        return Collections.emptyMap();
    }

}
