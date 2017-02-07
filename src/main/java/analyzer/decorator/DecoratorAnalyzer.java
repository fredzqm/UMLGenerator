package analyzer.decorator;

import analyzer.relationParser.RelationHasA;
import analyzer.utility.*;
import utility.MethodType;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by lamd on 2/2/2017.
 */
public class DecoratorAnalyzer extends DecoratorTemplate {
    private void addCommonDecoratorStyle(ISystemModel systemModel, IClassModel classModel) {
        systemModel.addClassModelStyle(classModel, "style", "filled");
        systemModel.addClassModelStyle(classModel, "fillcolor", "green");
    }

    @Override
    protected void styleParent(ISystemModel systemModel, IClassModel parent) {
        addCommonDecoratorStyle(systemModel, parent);
        systemModel.addClassModelSteretypes(parent, "component");
    }

    @Override
    protected void styleChild(ISystemModel systemModel, IClassModel child) {
        addCommonDecoratorStyle(systemModel, child);
        systemModel.addClassModelSteretypes(child, "decorator");
    }

    @Override
    protected void styleChildParentRelationship(ISystemModel systemModel, IClassModel child, IClassModel parent) {
        systemModel.addStyleToRelation(child, parent, RelationHasA.REL_KEY, "xlabel", "decorates");
    }

    @Override
    protected void updateRelatedClasses(ISystemModel systemModel, IClassModel decoratorClass) {
        systemModel.getClasses().forEach((classModel) -> {
            if (classModel.getSuperClass().equals(decoratorClass)) {
                addCommonDecoratorStyle(systemModel, classModel);
                systemModel.addClassModelSteretypes(classModel, "decorator");
            }
        });
    }

    @Override
    protected boolean detectPattern(IClassModel child, IClassModel parent) {
        return hasParentAsField(child, parent) && hasParentAsConstructorArgument(child, parent)
                && hasParentMethodMapped(child, parent);
    }
}
