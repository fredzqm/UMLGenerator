package analyzerRelationParser;

import analyzer.*;
import utility.IFilter;
import utility.IMapper;

import java.util.*;

/**
 * It decorates ISystem model and supplies extends, implements, has-a and
 * depends-on relationship
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
            Map<? extends IClassModel, Integer> has_a = getHasRelationship(classModel);
            for (IClassModel x : has_a.keySet())
                if (classList.contains(x))
                    addToMap(map, new ClassPair(classModel, x), new RelationHasA(has_a.get(x)));

            // add related depends on relationship
            Map<? extends IClassModel, Integer> depends_on = getDependsRelationship(classModel);
            for (IClassModel x : depends_on.keySet())
                if (classList.contains(x))
                    if (!has_a.containsKey(x))
                        addToMap(map, new ClassPair(classModel, x), new RelationDependsOn(depends_on.get(x)));
        }
        return map;
    }

    private Map<IClassModel, Integer> getHasRelationship(IClassModel classModel) {
        HashMap<IClassModel, Integer> map = new HashMap<>();
        Set<IClassModel> set = new HashSet<>();
        IFilter<IFieldModel> filter = (f) -> !f.isStatic();
        IMapper<IFieldModel, ITypeModel> mapper = IFieldModel::getFieldType;
        for (ITypeModel hasType : mapper.map(filter.filter(classModel.getFields()))) {
            checkType(hasType, map, set);
        }
        for (IClassModel c : set) {
            if (map.containsKey(c)) {
                map.put(c, -map.get(c));
            } else {
                map.put(c, 0);
            }
        }
        return map;
    }

    private Map<? extends IClassModel, Integer> getDependsRelationship(IClassModel classModel) {
        HashMap<IClassModel, Integer> map = new HashMap<>();
        Set<IClassModel> set = new HashSet<>();

        IFilter<IMethodModel> filter = (f) -> !f.isStatic();
        for (IMethodModel method : filter.filter(classModel.getMethods())) {
            List<? extends ITypeModel> args = method.getArguments();
            checkType(method.getReturnType(), map, set);
            for (ITypeModel t : args)
                checkType(t, map, set);
        }
        for (IClassModel c : set) {
            if (map.containsKey(c)) {
                map.put(c, -map.get(c));
            } else {
                map.put(c, 0);
            }
        }
        return map;
    }

    private void checkType(ITypeModel type, HashMap<IClassModel, Integer> map, Set<IClassModel> set) {
        IClassModel hasClass = type.getClassModel();
        if (type.getDimension() > 0) {
            if (hasClass != null)
                set.add(hasClass);
        }
        ITypeModel collection = type.assignTo("java.lang.Iterable");
        if (collection != null && collection.getGenericArgNumber() > 0) {
            // iterable of other things
            IClassModel hasManyClass = collection.getGenericArg(0).getClassModel();
            if (hasManyClass != null)
                set.add(hasManyClass);
        }
        if (hasClass != null) {
            // regular fields
            if (map.containsKey(hasClass)) {
                map.put(hasClass, map.get(hasClass) + 1);
            } else {
                map.put(hasClass, 1);
            }
        }
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
