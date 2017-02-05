package adapter.relationParser;

import analyzer.utility.*;
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
                    if (!has_a.containsKey(x) && !interfaces.contains(x) && !Objects.equals(superClass, x)
                            && !Objects.equals(classModel, x))
                        addToMap(map, new ClassPair(classModel, x), new RelationDependsOn(depends_on.get(x)));
        }
        return map;
    }

    /**
     * add the info to the relations map
     *
     * @param map  relations map
     * @param pair the class pair this relation is on
     * @param info the relationtype
     */
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
            ITypeModel collectionSuperType = hasType.assignTo("java.lang.Iterable");
            if (collectionSuperType != null && collectionSuperType.getGenericArgNumber() > 0) {
                IClassModel hasManyClass = collectionSuperType.getGenericArg(0).getClassModel();
                if (hasManyClass != null)
                    set.add(hasManyClass);
            }
            ITypeModel mapSuperType = hasType.assignTo("java.util.Map");
            if (mapSuperType != null && mapSuperType.getGenericArgNumber() > 0) {
                IClassModel hasManyClass = mapSuperType.getGenericArg(0).getClassModel();
                if (hasManyClass != null)
                    set.add(hasManyClass);
                hasManyClass = mapSuperType.getGenericArg(1).getClassModel();
                if (hasManyClass != null)
                    set.add(hasManyClass);
            }
            if (hasClass != null) {
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

        for (IMethodModel method : classModel.getMethods()) {
            List<? extends ITypeModel> args = method.getArguments();
            for (ITypeModel t : args)
                checkType(t, map);
            checkType(method.getReturnType(), map);
            for (IInstructionModel inst : method.getInstructions())
                for (ITypeModel t : inst.getDependentTypes())
                    checkType(t, map);
//            for (IFieldModel t : method.getAccessedFields()) {
//                checkClass(t.getBelongTo(), map);
//            }
//            for (IMethodModel m : method.getCalledMethods()) {
//                args = m.getArguments();
//                for (ITypeModel t : args)
//                    checkType(t, map);
//                checkType(m.getReturnType(), map);
//                checkClass(m.getBelongTo(), map);
//            }
        }
        return map;
    }

    private void checkType(ITypeModel type, HashMap<IClassModel, Boolean> map) {
        ITypeModel collectionSuperType = type.assignTo("java.lang.Iterable");
        if (collectionSuperType != null && collectionSuperType.getGenericArgNumber() > 0) {
            IClassModel hasManyClass = collectionSuperType.getGenericArg(0).getClassModel();
            if (hasManyClass != null)
                map.put(hasManyClass, true);
        }
        ITypeModel mapSuperType = type.assignTo("java.util.Map");
        if (mapSuperType != null && mapSuperType.getGenericArgNumber() > 0) {
            IClassModel hasManyClass = mapSuperType.getGenericArg(0).getClassModel();
            if (hasManyClass != null)
                map.put(hasManyClass, true);
            hasManyClass = mapSuperType.getGenericArg(1).getClassModel();
            if (hasManyClass != null)
                map.put(hasManyClass, true);
        }
        for (IClassModel c : type.getDependentClass())
            checkClass(c, map);
    }

    private void checkClass(IClassModel clazz, HashMap<IClassModel, Boolean> map) {
        if (!map.containsKey(clazz))
            map.put(clazz, false);
    }
}
