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
public class DecoratorAnalyzer extends AbstractAdapterDecoratorTemplate {
    private boolean hasParentAsField(IClassModel child, IClassModel parent) {
        Collection<? extends IFieldModel> fields = child.getFields();
        for (IFieldModel field : fields) {
            if (field.getFieldType().equals(parent)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasParentAsConstructorArgument(IClassModel child, IClassModel parent) {
        List<? extends ITypeModel> arguments;
        Collection<? extends IMethodModel> methods = child.getMethods();
        for (IMethodModel method : methods) {
            if (method.getMethodType() == MethodType.CONSTRUCTOR) {
                arguments = method.getArguments();
                for (ITypeModel type : arguments) {
                    if (type.getClassModel().equals(parent)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isDecoratedMethod(IMethodModel method, Collection<? extends IMethodModel> parentMethods) {
        for (IMethodModel parentMethod : parentMethods) {
            if (parentMethod.getSignature().equals(method.getSignature())) {
                return true;
            }
        }
        return false;
    }

    private boolean isParentFieldCalled(IClassModel parent, IMethodModel method) {
        for (IFieldModel field : method.getAccessedFields()) {
            if (field.getFieldType().equals(parent)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasParentMethodMapped(IClassModel child, IClassModel parent) {
        Collection<? extends IMethodModel> parentMethods = parent.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD).collect(Collectors.toList());

        Set<IMethodModel> decoratedMethods = new HashSet<>();
        child.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD && isDecoratedMethod(method, parentMethods) && isParentFieldCalled(parent, method))
                .forEach(decoratedMethods::add);

        return decoratedMethods.size() == parentMethods.size();
    }

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
//        systemModel.addStyleToRelation(child, parent, new RelationHasA(0), "xlabel", "decorates");
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
