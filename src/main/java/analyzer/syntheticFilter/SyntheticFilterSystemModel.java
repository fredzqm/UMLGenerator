package analyzer.syntheticFilter;

import java.util.HashSet;
import java.util.Set;

import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;
import analyzer.utility.ISystemModelFilter;

public class SyntheticFilterSystemModel extends ISystemModelFilter {
    SyntheticFilterSystemModel(ISystemModel systemModel) {
        super(systemModel);
    }

    @Override
    public Set<? extends IClassModel> getClasses() {
        Set<IClassModel> classes = new HashSet<>();
        super.getClasses().forEach((clazz) -> {
            if (!clazz.isSynthetic()) {
                classes.add(new SyntheticFilterClassModel(clazz));
            }
        });
        return classes;
    }
}
