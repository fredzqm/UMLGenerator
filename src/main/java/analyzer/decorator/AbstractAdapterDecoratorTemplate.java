package analyzer.decorator;

import analyzer.utility.IAnalyzer;
import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;
import config.IConfiguration;

import java.util.Collection;
import java.util.LinkedList;

/**
 * An Abstract Template for Adapter and Decorator pattern detection.
 * <p>
 * Created by lamd on 2/2/2017.
 */
public abstract class AbstractAdapterDecoratorTemplate implements IAnalyzer {
    @Override
    public final void analyze(ISystemModel systemModel, IConfiguration config) {
        updateModel(systemModel);
    }

    private Collection<IClassModel> getPotentialParents(Collection<? extends IClassModel> classes, IClassModel clazz) {
        Collection<IClassModel> potentialParents = new LinkedList<>();

        potentialParents.add(clazz.getSuperClass());
        clazz.getInterfaces().forEach(potentialParents::add);

        return potentialParents;
    }

    /**
     * Returns a Collection of ClassModel that are parents of the given ClassModel that fulfills the evaluation criteria
     * defined by the subclass.
     * <p>
     *
     * @param classes
     * @param child   IClassModel to be evaluated.
     * @return Collection of IClassModel of ParentClassModel defined by the subclass.
     */
    private void evaluateClass(ISystemModel systemModel, Collection<? extends IClassModel> classes, IClassModel child) {
        Collection<IClassModel> potentialParents = getPotentialParents(classes, child);
        potentialParents.add(child);
        for (IClassModel parent : potentialParents) {
            if (detectPattern(child, parent)) {
                styleParent(systemModel, parent);
                styleChild(systemModel, child);
                styleChildParentRelationship(systemModel, child, parent);
                updateRelatedClasses(systemModel, child);
            }
        }
    }

    /**
     * Define how to style the parent of the Child-to-Parent Relationship.
     *
     * @param systemModel ISystemModel that stores the styling information per class.
     * @param parent      IClassModel of the parent class in the Child-to-Parent Relationship.
     */
    protected abstract void styleParent(ISystemModel systemModel, IClassModel parent);

    /**
     * Define how to style the child of the Child-to-Parent Relationship.
     *
     * @param systemModel ISystemModel that stores the styling information per class.
     * @param child       IClassModel of the child class in the Child-to-Parent Relationship.
     */
    protected abstract void styleChild(ISystemModel systemModel, IClassModel child);

    /**
     * Define how to style the Child-to-Parent Relationship.
     *
     * @param systemModel ISystemModel that stores the styling information per class.
     * @param child       IClassModel of a child of a Superclass relation.
     * @param parent      IClassModel of a parent of a Superclass relation.
     */
    protected abstract void styleChildParentRelationship(ISystemModel systemModel, IClassModel child, IClassModel parent);

    /**
     * Updates Classes related the the class clazz if necessary. This is a hook method.
     *
     * @param systemModel ISystemModel that stores the styling information per class.
     * @param clazz       IClassModel of the child class with possible subclasses to be updated.
     */
    protected void updateRelatedClasses(ISystemModel systemModel, IClassModel clazz) {
        // Hook
    }

    private void updateModel(ISystemModel systemModel) {
        Collection<? extends IClassModel> classes = systemModel.getClasses();
        for (IClassModel clazz : classes) {
            evaluateClass(systemModel, classes, clazz);
        }
    }

    /**
     * Evaluates a given parent class and the child class and determine whether they meet the desired pattern criteria.
     * <p>
     * For example: decorator detection may check if child has a field of the parent,
     * a constructor that takes the field as an argument, and if the child overrides each of the parent's
     * methods where the child method's body uses the field of the parent type.
     *
     * @param child  IClassModel of the dependent Relation.
     * @param parent IClassModel of the depended Relation.
     * @return true if the parent and child should be updated for this analyzer.
     */
    protected abstract boolean detectPattern(IClassModel child, IClassModel parent);
}
