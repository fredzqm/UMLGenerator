package analyzer.syntheticFilter;

import analyzer.utility.IAnalyzer;
import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;
import config.IConfiguration;

import java.util.HashSet;
import java.util.Set;

public class SyntheticFilterAnalyzer implements IAnalyzer {
    @Override
    public void analyze(ISystemModel systemModel, IConfiguration config) {
        Set<IClassModel> classes = new HashSet<>();
        systemModel.getClasses().stream()
                .filter((clazz) -> !clazz.isSynthetic())
                .forEach((clazz) -> classes.add(new SyntheticFilterClassModel(clazz)));

        systemModel.setClasses(classes);
    }
}