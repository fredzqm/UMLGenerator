package analyzer.utility;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ProcessedSystemModel implements ISystemModel {
    private Collection<? extends IClassModel> classes;
    private Map<ClassPair, List<IRelationInfo>> relations;

    public ProcessedSystemModel(Collection<? extends IClassModel> classes,
                                Map<ClassPair, List<IRelationInfo>> relations) {
        this.classes = classes;
        this.relations = relations;
    }

    @Override
    public Collection<? extends IClassModel> getClasses() {
        return this.classes;
    }

    @Override
    public Map<ClassPair, List<IRelationInfo>> getRelations() {
        return this.relations;
    }

}
