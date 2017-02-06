package analyzer.utility;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SystemModel implements ISystemModel {
    private Set<IClassModel> classSet;

    private Map<IClassModel, String> nodeStyle;
    private Map<IClassModel, List<String>> prototypes;
    private Map<IClassModel, Map<IClassModel, List<IRelationInfo>>> relations;


    public SystemModel(Set<IClassModel> importantList) {
        this.classSet = importantList;
    }

    public Set<IClassModel> getClasses() {
        return classSet;
    }

    @Override
    public Map<ClassPair, List<IRelationInfo>> getRelations() {
        return null;
    }


}
