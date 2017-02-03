package analyzer.decorator;

import analyzer.utility.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lamd on 2/2/2017.
 */
public abstract class AbstractAdapterDecoratorTemplate extends ISystemModelFilter {

    /**
     * Construct a ISystemModel Filter.
     *
     * @param systemModel
     */
    public AbstractAdapterDecoratorTemplate(ISystemModel systemModel) {
        super(systemModel);
    }

    @Override
    public final Collection<? extends IClassModel> getClasses() {
        super.getClasses().forEach(this::evaluateClass);

        return ;
    }

    @Override
    public final Map<ClassPair, List<IRelationInfo>> getRelations() {
        return null;
    }

    protected Collection<IClassModel> evaluateClass(IClassModel clazz) {
        Collection<IClassModel> potentialParents = getPotentialParents(clazz);
        Collection<IClassModel> validatedParents = potentialParents.stream()
                .filter((parent) -> evaluateParent(clazz, parent))      // Subclasses define how to filter.
                .map(this::createParentClassModel)                      // Create a ClassModel for each of the filtered parents.
                .collect(Collectors.toCollection());                    // Collect the results into a Collection type.

        if (validatedParents.isEmpty()) {
            return validatedParents;
        }
        validatedParents.add(createChildClassModel(clazz));
        return (validatedParents.isEmpty()) ? validatedParents : validatedParents.(clazz);
    }

    /**
     * Evaluates a given parent class and the child and detect whether they meet the desired pattern.
     *
     * For example: decorator detection may check if child has a field of the parent,
     * a constructor that takes the field as an argument, and if the child overrides each of the parent's
     * methods where the child method's body uses the field of the parent type.
     *
     * @param methods
     * @param parent
     * @return
     */
    abstract boolean evaluateParent(IClassModel child, IClassModel parent);

    abstract IClassModel createParentClassModel(IClassModel validatedParent);

    abstract IClassModel createChildClassModel(IClassModel child);


    private Collection<IClassModel> getPotentialParents(IClassModel clazz) {
        Collection<IClassModel> potentialParents = new ArrayList<>();
        potentialParents.add(clazz.getSuperClass());
        clazz.getInterfaces().forEach(potentialParents::add);

        return potentialParents;
    }
}
