package analyzer.relationParser;

import analyzer.utility.ClassPair;
import analyzer.utility.IRelationInfo;
import analyzer.utility.ISystemModel;
import analyzer.utility.ISystemModelFilter;

import java.util.*;

public class MergeRelationSystemModel extends ISystemModelFilter {
    /**
     * Construct MergeRelationSystemModel.
     *
     * @param systemModel
     */
    MergeRelationSystemModel(ISystemModel systemModel) {
        super(systemModel);
    }

    @Override
    public Map<ClassPair, List<IRelationInfo>> getRelations() {
        Map<ClassPair, List<IRelationInfo>> oldMap = super.getRelations();
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