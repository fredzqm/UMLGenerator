package analyzer.syntheticFilter;

import analyzer.utility.IAnalyzer;
import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;
import analyzer.utility.ProcessedSystemModel;
import config.IConfiguration;

import java.util.HashSet;
import java.util.Set;

public class SyntheticFilterAnalyzer implements IAnalyzer {

    @Override
    public ISystemModel analyze(ISystemModel systemModel, IConfiguration config) {
        return new ProcessedSystemModel(getClasses(systemModel.getClasses()), systemModel.getRelations());
    }

    public Set<? extends IClassModel> getClasses(Set<? extends IClassModel> classList) {
        Set<IClassModel> classes = new HashSet<>();
        classList.forEach((clazz) -> {
            if (!clazz.isSynthetic()) {
                classes.add(new SyntheticFilterClassModel(clazz));
            }
        });
        return classes;
    }
}
