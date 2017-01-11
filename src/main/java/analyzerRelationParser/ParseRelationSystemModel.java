package analyzerRelationParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import analyzer.ClassPair;
import analyzer.IClassModel;
import analyzer.IFieldModel;
import analyzer.IMethodModel;
import analyzer.IRelationInfo;
import analyzer.ISystemModel;
import analyzer.ISystemModelFilter;
import analyzer.ITypeModel;
import utility.IFilter;
import utility.IMapper;

/**
 * It decorates ISystem model and supplies extends, implements, has-a and
 * depends-on relationship
 * 
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
            Collection<? extends IClassModel> interfaces = classModel.getInterfaces();
            for (IClassModel x : interfaces)
                if (classList.contains(x))
                    addToMap(map, new ClassPair(classModel, x), new RelationImplement());

            // add related has-a relationship
            Map<? extends IClassModel, Integer> has_a = getHasRelationship(classModel);
            for (IClassModel x : has_a.keySet())
                if (classList.contains(x))
                    addToMap(map, new ClassPair(classModel, x), new RelationHasA(has_a.get(x)));

            // add related depends on relationship
            Map<? extends IClassModel, Boolean> depends_on = getDependsRelationship(classModel);
            for (IClassModel x : depends_on.keySet())
                if (classList.contains(x))
                    if (!has_a.containsKey(x) && !interfaces.contains(x) && superClass != x)
                        addToMap(map, new ClassPair(classModel, x), new RelationDependsOn(depends_on.get(x)));
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

    private Map<IClassModel, Integer> getHasRelationship(IClassModel classModel) {
        HashMap<IClassModel, Integer> map = new HashMap<>();
        Set<IClassModel> set = new HashSet<>();
        IFilter<IFieldModel> filter = (f) -> !f.isStatic();
        IMapper<IFieldModel, ITypeModel> mapper = IFieldModel::getFieldType;
        for (ITypeModel hasType : mapper.map(filter.filter(classModel.getFields()))) {
            IClassModel hasClass = hasType.getClassModel();
            if (hasType.getDimension() > 0) {
                if (hasClass != null)
                    set.add(hasClass);
            }
            ITypeModel collection = hasType.assignTo("java.lang.Iterable");
            if (collection != null && collection.getGenericArgNumber() > 0) {
                // Iterable of other things
                IClassModel hasManyClass = collection.getGenericArg(0).getClassModel();
                if (hasManyClass != null)
                    set.add(hasManyClass);
            }
            if (hasClass != null) {
                // regular class
                if (map.containsKey(hasClass)) {
                    map.put(hasClass, map.get(hasClass) + 1);
                } else {
                    map.put(hasClass, 1);
                }
            }
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

    private Map<? extends IClassModel, Boolean> getDependsRelationship(IClassModel classModel) {
        HashMap<IClassModel, Boolean> map = new HashMap<>();

        IFilter<IMethodModel> filter = (f) -> !f.isStatic();
        for (IMethodModel method : filter.filter(classModel.getMethods())) {
            List<? extends ITypeModel> args = method.getArguments();
            checkType(method.getReturnType(), map);
            for (ITypeModel t : args)
                checkType(t, map);
            for (IFieldModel t : method.getAccessedFields()) {
                checkClass(t.getBelongTo(), map);
            }
            for (IMethodModel m : method.getCalledMethods()) {
                checkClass(m.getBelongTo(), map);
            }
        }
        return map;
    }

    private void checkType(ITypeModel type, HashMap<IClassModel, Boolean> map) {
        IClassModel hasClass = type.getClassModel();
        if (type.getDimension() > 0) {
            if (hasClass != null)
                map.put(hasClass, true);
        }
        ITypeModel collection = type.assignTo("java.lang.Iterable");
        if (collection != null && collection.getGenericArgNumber() > 0) {
            // iterable of other things
            IClassModel hasManyClass = collection.getGenericArg(0).getClassModel();
            if (hasManyClass != null)
                map.put(hasManyClass, true);
        }
        if (hasClass != null) {
            checkClass(hasClass, map);
        }
    }

    private void checkClass(IClassModel clazz, HashMap<IClassModel, Boolean> map) {
        if (!map.containsKey(clazz))
            map.put(clazz, false);
    }
}
