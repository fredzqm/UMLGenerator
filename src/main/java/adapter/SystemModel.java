package adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import analyzer.relationParser.IRelationInfo;
import analyzer.utility.ClassPair;
import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;
import analyzer.utility.StyleMap;

public class SystemModel implements ISystemModel {
    private Set<IClassModel> classSet;

    private Map<IClassModel, StyleMap> nodeStyle;
    private Map<IClassModel, List<String>> stereotypes;
    private Map<ClassPair, Map<String, StyleMap>> relations;

    public SystemModel(Set<IClassModel> importantList) {
        this.classSet = importantList;
        nodeStyle = new HashMap<>();
        relations = new HashMap<>();
        stereotypes = new HashMap<>();
    }

    @Override
    public void setClasses(Set<IClassModel> classSet) {
        this.classSet = classSet;
    }

    public Set<IClassModel> getClasses() {
        return classSet;
    }

    public Map<ClassPair, Map<String, StyleMap>> getRelations() {
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
    public void addStyleToRelation(IClassModel from, IClassModel to, String relKey, String key, String value) {
        ClassPair pair = new ClassPair(from, to);
        if (!relations.containsKey(pair))
            relations.put(pair, new HashMap<>());
        Map<String, StyleMap> map = relations.get(pair);
        if (!map.containsKey(relKey))
            map.put(relKey, new StyleMap());
        map.get(relKey).addStyle(key, value);
    }

    @Override
    public void addRelation(IClassModel from, IClassModel to, String relKey, StyleMap styleMap) {
        ClassPair pair = new ClassPair(from, to);
        if (!relations.containsKey(pair))
            relations.put(pair, new HashMap<>());
        Map<String, StyleMap> map = relations.get(pair);
        if (!map.containsKey(relKey))
            map.put(relKey, styleMap);
    }

}
