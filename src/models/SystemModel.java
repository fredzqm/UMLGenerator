package models;

import java.util.ArrayList;
import java.util.List;

public class SystemModel implements generator.ISystemModel {
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
