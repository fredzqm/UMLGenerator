package model;

import analyzer.IAnalyzerSystemModel;
import generator.ISystemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class representing the entire model of a java program
 */
public class SystemModel implements ISystemModel, IAnalyzerSystemModel {
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
        ASMClassTracker asmParser = ASMParser.getInstance(config);

        List<ClassModel> ls = new ArrayList<>();
        asmParser.getClasses().forEach((c) -> {
            ls.add(c);
        });
        return new SystemModel(ls);
    }

    @Override
    public Iterable<ClassModel> getClasses() {
        return importantList;
    }

}
