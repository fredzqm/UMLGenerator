package analyzer.syntheticFilter;

import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;
import analyzer.utility.ISystemModelFilter;

import java.util.ArrayList;
import java.util.Collection;

public class SyntheticFilterSystemModel extends ISystemModelFilter {
    SyntheticFilterSystemModel(ISystemModel systemModel) {
        super(systemModel);
    }

    @Override
    public Collection<? extends IClassModel> getClasses() {
        Collection<IClassModel> classes = new ArrayList<>();
        super.getClasses().forEach((clazz) -> {
            if (!clazz.isSynthetic()) {
                classes.add(new SyntheticFilterClassModel(clazz));
            }
        });
        return classes;
    }
}
