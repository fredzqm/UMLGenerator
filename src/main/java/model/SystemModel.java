package model;

import analyzer.IAnalyzerSystemModel;
import generator.IGeneratorSystemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Fred and all public methods.
 */
public class SystemModel implements IGeneratorSystemModel, IAnalyzerSystemModel {
    private ASMServiceProvider asmServiceProvider;

    private List<ClassModel> importantClasses;

    public SystemModel(Iterable<String> classNameList, ASMServiceProvider asmParser) {
        asmServiceProvider = asmParser;
        importantClasses = new ArrayList<>();
        for (String className : classNameList) {
            importantClasses.add(asmServiceProvider.getClassByName(className));
        }
    }

    @Override
    public List<ClassModel> getClasses() {
        return importantClasses;
    }

}
