package analyzerRelationParser;

import analyzer.*;

import java.util.*;

/**
 * TODO: Fred documentation.
 */
public class ParseRelationSystemModel extends ISystemModelFilter {
    /**
     * Construct ParseRelationSystemModel.
     *
     * @param systemModel
     */
    ParseRelationSystemModel(ISystemModel systemModel) {
        super(systemModel);
    }

    public Map<ClassPair, List<IRelationInfo>> getRelations() {
        Map<ClassPair, List<IRelationInfo>> map = new HashMap<>();
        Collection<? extends IClassModel> classList = getClasses();
        for (IClassModel classModel : classList) {
            // add related super class relationship
            IClassModel superClass = classModel.getSuperClass();
            if (superClass != null)
                if (classList.contains(superClass))
                    addToMap(map, new ClassPair(classModel, superClass), new RelationExtendsClass());

            // add related interface relationship
            Iterable<? extends IClassModel> interfaces = classModel.getInterfaces();
            for (IClassModel x : interfaces)
                if (classList.contains(x))
                    addToMap(map, new ClassPair(classModel, x), new RelationImplement());

            // add related has-a relationship
            Map<? extends IClassModel, Integer> has_a = classModel.getHasRelation();
            for (IClassModel x : has_a.keySet())
                if (classList.contains(x))
                    addToMap(map, new ClassPair(classModel, x), new RelationHasA(has_a.get(x)));

            // add related depends on relationship
            Iterable<? extends IClassModel> depends_on = classModel.getClassDependsOn();
            for (IClassModel x : depends_on)
                if (classList.contains(x))
                    addToMap(map, new ClassPair(classModel, x), new RelationDependsOn());
        }
        return map;
    }

    private void addToMap(Map<ClassPair, List<IRelationInfo>> map, ClassPair pair, IRelationInfo info) {
        if (map.containsKey(pair)) {
            map.get(pair).add(info);
        } else {
            List<IRelationInfo> ls2 = pair.isLoop() ? new ArrayList<>() : new LinkedList<>();
            ls2.add(info);
            map.put(pair, ls2);
        }
    }
}
