package analyzer.utility;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProcessedSystemModel implements ISystemModel {
    private Set<? extends IClassModel> classes;
    private Map<ClassPair, List<IRelationInfo>> relations;

    public ProcessedSystemModel(Set<? extends IClassModel> classes, Map<ClassPair, List<IRelationInfo>> relations) {
        this.classes = classes;
        this.relations = relations;
    }

    @Override
    public Set<? extends IClassModel> getClasses() {
        return classes;
    }

    @Override
    public Map<ClassPair, List<IRelationInfo>> getRelations() {
        return this.relations;
    }

}
