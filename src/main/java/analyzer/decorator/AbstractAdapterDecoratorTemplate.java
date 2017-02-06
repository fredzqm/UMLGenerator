package analyzer.decorator;

import analyzer.utility.IAnalyzer;
import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;
import config.IConfiguration;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by lamd on 2/2/2017.
 */
public abstract class AbstractAdapterDecoratorTemplate implements IAnalyzer {
    @Override
    public final void analyze(ISystemModel systemModel, IConfiguration config) {
        updateClasses(systemModel);
//        updateRelations(relations);
    }

    private Collection<IClassModel> getPotentialParents(Collection<? extends IClassModel> classes, IClassModel clazz) {
        Collection<IClassModel> potentialParents = new LinkedList<>();

        // Put clazz's super class and interfaces into the potential parent's Collection.
        potentialParents.add(clazz.getSuperClass());
        clazz.getInterfaces().forEach(potentialParents::add);

        return potentialParents;
    }

    /**
     * Returns a Collection of ClassModel that are parents of the given ClassModel that fulfills the evaluation criteria
     * defined by the subclass.
     * <p>
     * The Collection may be empty.
     *
     * @param classes
     * @param child   IClassModel to be evaluated.
     * @return Collection of IClassModel of ParentClassModel defined by the subclass.
     */
    private void evaluateClass(ISystemModel systemModel, Collection<? extends IClassModel> classes, IClassModel child) {
        Collection<IClassModel> potentialParents = getPotentialParents(classes, child);

        for (IClassModel parent : potentialParents) {
            if (evaluateParent(child, parent)) {
                styleParent(systemModel, parent);
                styleChild(systemModel, child);
                styleChildParentRelationship(systemModel, child, parent);
                updateRelatedClasses(systemModel, child);
            }
        }
    }

    protected abstract void styleParent(ISystemModel systemModel, IClassModel parent);

    protected abstract void styleChild(ISystemModel systemModel, IClassModel child);

    protected abstract void styleChildParentRelationship(ISystemModel systemModel, IClassModel child, IClassModel parent);

    protected abstract void updateRelatedClasses(ISystemModel systemModel, IClassModel clazz);

    private void updateClasses(ISystemModel systemModel) {
        Collection<? extends IClassModel> classes = systemModel.getClasses();
        for (IClassModel clazz : classes) {
            evaluateClass(systemModel, classes, clazz);
        }
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
}
