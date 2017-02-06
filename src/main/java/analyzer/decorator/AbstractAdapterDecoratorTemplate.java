package analyzer.decorator;

import analyzer.utility.*;
import config.IConfiguration;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lamd on 2/2/2017.
 */
public abstract class AbstractAdapterDecoratorTemplate implements IAnalyzer {
    @Override
    public final ISystemModel analyze(ISystemModel systemModel, IConfiguration config) {
        Set<? extends IClassModel> classes = systemModel.getClasses();
        Map<ClassPair, List<IRelationInfo>> relations = systemModel.getRelations();

        Map<IClassModel, Collection<IClassModel>> updateMap = createUpdateMap(classes);
        classes = updateClasses(updateMap);
        relations = updateRelations(updateMap, relations);

        return new ProcessedSystemModel(classes, relations);
    }

    private Collection<IClassModel> getPotentialParents(Collection<? extends IClassModel> classes, IClassModel clazz) {
        Collection<IClassModel> potentialParents = new LinkedList<>();
        Collection<IClassModel> candidates = new LinkedList<>();

        potentialParents.add(clazz.getSuperClass());
        clazz.getInterfaces().forEach(potentialParents::add);

        for (IClassModel classModel : potentialParents) {
            for (IClassModel fullModels : classes) {
                if (fullModels.equals(classModel)) {
                    candidates.add(fullModels);
                    break;
                }
            }
        }

        return candidates;
    }

    /**
     * Evaluates a given parent class and the child and detect whether they meet the desired pattern.
     * <p>
     * For example: decorator detection may check if child has a field of the parent,
     * a constructor that takes the field as an argument, and if the child overrides each of the parent's
     * methods where the child method's body uses the field of the parent type.
     *
     * @param child  IClassModel of the dependent Relation.
     * @param parent IClassModel of the depended Relation.
     * @return true if the parent and child should be updated for this analyzer.
     */
    protected abstract boolean evaluateParent(IClassModel child, IClassModel parent);

    /**
     * Constructs a IClassModel for all matched parents.
     *
     * @param validatedParent IClassModel of a class that has been validated by evaluateParent.
     * @return IClassModel for the depended relation.
     */
    protected abstract IClassModel createParentClassModel(IClassModel validatedParent);

    /**
     * Returns a Collection of ClassModel that are parents of the given ClassModel that fulfills the evaluation criteria
     * defined by the subclass.
     * <p>
     * The Collection may be empty.
     *
     *
     * @param classes
     * @param clazz IClassModel to be evaluated.
     * @return Collection of IClassModel of ParentClassModel defined by the subclass.
     */
    private Collection<IClassModel> evaluateClass(Collection<? extends IClassModel> classes, IClassModel clazz) {
        Collection<IClassModel> potentialParents = getPotentialParents(classes, clazz);
        return potentialParents.stream()
                .filter((parent) -> evaluateParent(clazz, parent))      // Subclasses define how to filter.
                .map(this::createParentClassModel)                      // Create a ClassModel for each of the filtered parents.
                .collect(Collectors.toList());                          // Collect the results into a Collection List.
    }

    private Map<IClassModel, Collection<IClassModel>> createUpdateMap(Collection<? extends IClassModel> classes) {
        Map<IClassModel, Collection<IClassModel>> updateMap = new HashMap<>();

        for (IClassModel clazz : classes) {
            updateMap.put(clazz, evaluateClass(classes, clazz));
        }

        return updateMap;
    }

    /**
     * Constructs a IClassModel for all matched child.
     *
     * @param child IClassModel of a child with matched parents defined by evaluateParent.
     * @return IClassModel for the dependent relation.
     */
    protected abstract IClassModel createChildClassModel(IClassModel child);

    /**
     * Hook method for subclasses to override. This is intended for subclasses to add final edits to the classModel Collection
     * for the given clazz.
     * <p>
     * For example: If a user wishes to also identify classes that extends some abstract decorator, then this method should be
     * overriden and return an updated list of the IClassModel.
     *
     * @param updatedClasses Collection of updatedClasses
     * @param clazz          IClassModel to be updated.
     * @return Updated Collection of IClassModel.
     */
    protected Set<IClassModel> updateRelatedClasses(Set<IClassModel> updatedClasses, IClassModel clazz) {
        return updatedClasses;
    }

    private Set<? extends IClassModel> updateClasses(Map<IClassModel, Collection<IClassModel>> updateMap) {
        Set<IClassModel> updatedClasses = new HashSet<>(); // We are only inserting. Avoid worst case.

        Collection<IClassModel> matchedClasses;
        for (IClassModel clazz : updateMap.keySet()) {
            matchedClasses = updateMap.get(clazz);

            if (!matchedClasses.isEmpty()) {
                for (IClassModel match : matchedClasses) {
                    updatedClasses.add(match);
                }
                updatedClasses.add(createChildClassModel(clazz));

                // Hook.
                updatedClasses = updateRelatedClasses(updatedClasses, clazz);
            } else {
                // It is a normal class if nothing is matched.
//                updatedClasses.add(clazz);
                if (!updatedClasses.contains(clazz)) {
                    updatedClasses.add(clazz);
                }
            }
        }

        return updatedClasses;
    }

    /**
     * Create a Relation between the child and parent ClassModel.
     *
     * @param info Current IRelationInfo between child to parent.
     * @return Decorated Relation between child and parent.
     */
    protected abstract IRelationInfo createRelation(IRelationInfo info);

    /**
     * Hook method for subclasses to override. This is intended for subclasses to add final edits to the Relations Map for
     * the given clazz.
     * <p>
     * For example: If a user wishes to also modify the relations of classes that extends some detected abstract decorator,
     * then this method should be overriden and return an Relations Map.
     *
     * @param updatedRelations Map of UpdatedRelations.
     * @param clazz            IClassModel of relation to update Map with.
     * @return Updated Relations Map with changes of clazz.
     */
    protected Map<ClassPair, List<IRelationInfo>> updateRelatedRelations(Map<ClassPair, List<IRelationInfo>> updatedRelations, IClassModel clazz) {
        return updatedRelations;
    }

    private Map<ClassPair, List<IRelationInfo>> updateRelations(Map<IClassModel, Collection<IClassModel>> updateMap, Map<ClassPair, List<IRelationInfo>> relations) {
        // Create the copy of the relations Map.
        Map<ClassPair, List<IRelationInfo>> updatedRelations = new HashMap<>();
        updatedRelations.putAll(relations);

        Collection<IClassModel> matchedClasses;
        ClassPair pair;
        List<IRelationInfo> infos, newInfos;
        for (IClassModel clazz : updateMap.keySet()) {
            matchedClasses = updateMap.get(clazz);

            // If match is not empty, this loop will update the updatedRelations
            // corresponding clazz -> match ClassPair.
            if (!matchedClasses.isEmpty()) {
                for (IClassModel match : matchedClasses) {
                    pair = new ClassPair(clazz, match);

                    newInfos = new LinkedList<>();
                    infos = relations.get(pair);
                    for (IRelationInfo info : infos) {
                        newInfos.add(createRelation(info));
                    }

                    updatedRelations.put(pair, newInfos);
                }

                // Hook: Allow subclasses to modify relations related to clazz.
                updatedRelations = updateRelatedRelations(updatedRelations, clazz);
            }
        }

        return updatedRelations;
    }
}
