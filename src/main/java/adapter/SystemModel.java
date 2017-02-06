package adapter;

import analyzer.utility.*;

import java.util.*;

public class SystemModel implements ISystemModel {
    private Set<IClassModel> classSet;

    private Map<IClassModel, StyleMap> nodeStyle;
    private Map<IClassModel, List<String>> stereotypes;
    private Map<ClassPair, Map<Class<? extends IRelationInfo>, IRelationInfo>> relations;

    public SystemModel(Set<IClassModel> importantList) {
        this.classSet = importantList;
        nodeStyle = new HashMap<>();
        relations = new HashMap<>();
        stereotypes = new HashMap<>();
    }

    public Set<IClassModel> getClasses() {
        return classSet;
    }

    @Override
    public void setClasses(Set<IClassModel> classSet) {
        this.classSet = classSet;
    }

    public Map<ClassPair, Map<Class<? extends IRelationInfo>, IRelationInfo>> getRelations() {
        return relations;
    }

    @Override
    public List<String> getStereoTypes(IClassModel c) {
        return stereotypes.getOrDefault(c, Collections.emptyList());
    }

    @Override
    public void addClassModelStyle(IClassModel clazz, String key, String value) {
        if (!nodeStyle.containsKey(clazz))
            nodeStyle.put(clazz, new StyleMap());
        nodeStyle.get(clazz).addStyle(key, value);
    }

    @Override
    public String getNodeStyle(IClassModel clazz) {
        if (nodeStyle.containsKey(clazz))
            return nodeStyle.get(clazz).getStyleString();
        return "";
    }

    @Override
    public void addClassModelSteretypes(IClassModel clazz, String stereotype) {
        if (!stereotypes.containsKey(clazz))
            stereotypes.put(clazz, new ArrayList<>());
        stereotypes.get(clazz).add(stereotype);
    }

    @Override
    public void addStyleToRelation(IClassModel from, IClassModel to, IRelationInfo relInfo, String key, String value) {
        ClassPair pair = new ClassPair(from, to);
        if (!relations.containsKey(pair))
            relations.put(pair, new HashMap<>());
        Map<Class<? extends IRelationInfo>, IRelationInfo> map = relations.get(pair);
        if (!map.containsKey(relInfo.getClass()))
            map.put(relInfo.getClass(), relInfo);
        map.get(relInfo.getClass()).addStyle(key, value);
    }

    @Override
    public void addRelation(IClassModel from, IClassModel to, IRelationInfo relInfo) {
        ClassPair pair = new ClassPair(from, to);
        if (!relations.containsKey(pair))
            relations.put(pair, new HashMap<>());
        Map<Class<? extends IRelationInfo>, IRelationInfo> map = relations.get(pair);
        if (!map.containsKey(relInfo.getClass()))
            map.put(relInfo.getClass(), relInfo);
    }

}
