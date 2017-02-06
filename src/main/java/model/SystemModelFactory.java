package model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import analyzer.utility.IClassModel;
import config.IConfiguration;

/**
 * This class representing the entire model of a java program
 *
 * @author zhang
 */
public class SystemModelFactory {

    /**
     * constructs an instance of SystemModel given the configuration
     *
     * @param config
     * @return
     */
    public static Set<IClassModel> getInstance(ModelConfiguration config) {
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

        return new HashSet<>(ls);
    }

    public static Set<IClassModel> getInstance(IConfiguration iConfig) {
        ModelConfiguration config = iConfig.createConfiguration(ModelConfiguration.class);
        return getInstance(config);
    }

}
