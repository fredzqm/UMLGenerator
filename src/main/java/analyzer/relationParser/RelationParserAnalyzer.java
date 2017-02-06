package analyzer.relationParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import analyzer.utility.ClassPair;
import analyzer.utility.IAnalyzer;
import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IInstructionModel;
import analyzer.utility.IMethodModel;
import analyzer.utility.IRelationInfo;
import analyzer.utility.ISystemModel;
import analyzer.utility.ITypeModel;
import config.IConfiguration;
import utility.IFilter;
import utility.IMapper;

import java.util.*;

public class RelationParserAnalyzer implements IAnalyzer {
    @Override
    public void analyze(ISystemModel sm, IConfiguration config) {
        Set<? extends IClassModel> classList = sm.getClasses();
        Map<ClassPair, List<IRelationInfo>> map = mergeRelations(generateRelation(classList));
        for (ClassPair pair : map.keySet()) {
            for (IRelationInfo info : map.get(pair)) {
                sm.addRelation(pair.getFrom(), pair.getTo(), info);
            }
        }
    }

    public Map<ClassPair, List<IRelationInfo>> generateRelation(Set<? extends IClassModel> classList) {
        Map<ClassPair, List<IRelationInfo>> map = new HashMap<>();
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

    public Map<ClassPair, List<IRelationInfo>> mergeRelations(Map<ClassPair, List<IRelationInfo>> oldMap) {
        Map<ClassPair, List<IRelationInfo>> newMap = new HashMap<>();

        // go through the map merge or remove relation according to rules
        ClassPair next;
        while (!oldMap.isEmpty()) {
            next = oldMap.keySet().iterator().next();
            List<IRelationInfo> a = oldMap.get(next);
            if (next.isLoop()) {
                mergeLoopRelation(newMap, next, a);
            } else {
                mergeBijectiveRelation(oldMap, newMap, next, a);
            }
            addToMap(newMap, next, a);
            oldMap.remove(next);
        }
        return newMap;
    }

    private void mergeBijectiveRelation(Map<ClassPair, List<IRelationInfo>> oldMap,
                                        Map<ClassPair, List<IRelationInfo>> newMap, ClassPair next, List<IRelationInfo> a) {
        ClassPair reverse = next.reverse();
        List<IRelationInfo> b;
        ListIterator<IRelationInfo> aitr, bitr;
        IRelationInfo aRel, bRel, rel;
        if (oldMap.containsKey(reverse)) {
            b = oldMap.get(reverse);
            aitr = a.listIterator();
            while (aitr.hasNext()) {
                aRel = aitr.next();
                bitr = b.listIterator();
                while (bitr.hasNext()) {
                    bRel = bitr.next();
                    rel = merge(aRel, bRel);
                    if (rel != null) {
                        aitr.remove();
                        bitr.remove();
                        addToMap(newMap, next, rel);
                    }
                }
            }
            addToMap(newMap, reverse, b);
            oldMap.remove(reverse);
        }
    }

    private void mergeLoopRelation(Map<ClassPair, List<IRelationInfo>> newMap, ClassPair next, List<IRelationInfo> a) {
        IRelationInfo rel;
        for (int i = 0; i < a.size(); i++) {
            for (int j = i + 1; j < a.size(); j++) {
                rel = merge(a.get(i), a.get(j));
                if (rel != null) {
                    a.remove(j);
                    a.remove(i);
                    j -= 2;
                    i -= 1;
                    addToMap(newMap, next, rel);
                }
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

    private void addToMap(Map<ClassPair, List<IRelationInfo>> map, ClassPair pair, List<IRelationInfo> ls) {
        if (map.containsKey(pair)) {
            map.get(pair).addAll(ls);
        } else {
            List<IRelationInfo> ls2 = pair.isLoop() ? new ArrayList<>(ls) : new LinkedList<>(ls);
            map.put(pair, ls2);
        }
    }

    private IRelationInfo merge(IRelationInfo aRel, IRelationInfo bRel) {
        if (aRel.getClass() == bRel.getClass()) {
            if (aRel instanceof RelationDependsOn) {
                return new RelationBijectiveDecorator(aRel);
            } else if (aRel instanceof RelationHasA) {
                return new RelationHasABijective((RelationHasA) aRel, (RelationHasA) bRel);
            }
        }
        return null;
    }

}
