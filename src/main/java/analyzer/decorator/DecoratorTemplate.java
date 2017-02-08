package analyzer.decorator;

import analyzer.relationParser.RelationHasA;
import analyzer.utility.*;
import model.Signature;
import utility.MethodType;

import java.util.Collection;
import java.util.List;

/**
 * A Decorator Abstract class that contains basic utility methods used by both Good and Bad Decorator Analyzers.
 * <p>
 * Created by lamd on 2/7/2017.
 */
public abstract class DecoratorTemplate extends AdapterDecoratorTemplate {
    protected boolean hasParentAsField(IClassModel child, IClassModel parent) {
        Collection<? extends IFieldModel> fields = child.getFields();
        for (IFieldModel field : fields) {
            if (field.getFieldType().equals(parent)) {
                return true;
            }
        }
        return false;
    }

    protected boolean hasParentAsConstructorArgument(IClassModel child, IClassModel parent) {
        List<? extends ITypeModel> arguments;
        Collection<? extends IMethodModel> methods = child.getMethods();
        for (IMethodModel method : methods) {
            if (method.getMethodType() == MethodType.CONSTRUCTOR) {
                arguments = method.getArguments();
                for (ITypeModel type : arguments) {
                    if (parent.equals(type)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected boolean isDecoratedMethod(IMethodModel method, Collection<? extends IMethodModel> parentMethods) {
        Signature methodSignature = method.getSignature();
        for (IMethodModel parentMethod : parentMethods) {
            if (parentMethod.getSignature().equals(methodSignature)) {
                System.out.println("\tparentMethod: " + parentMethod);
                System.out.println("\tchildMethod: " + methodSignature);
                return true;
            }
        }
        return false;
    }

    protected boolean isParentFieldCalled(IClassModel parent, IMethodModel method) {
        for (IFieldModel field : method.getAccessedFields()) {
            if (field.getFieldType().equals(parent)) {
                return true;
            }
        }

        return false;
    }

    private void addCommonDecoratorStyle(ISystemModel systemModel, IClassModel classModel) {
        systemModel.addClassModelStyle(classModel, "style", "filled");
        systemModel.addClassModelStyle(classModel, "fillcolor", this.config.getFillColor());
    }

    @Override
    protected void styleParent(ISystemModel systemModel, IClassModel parent) {
        addCommonDecoratorStyle(systemModel, parent);
        systemModel.addClassModelSteretypes(parent, this.config.getParentStereotype());
    }

    @Override
    protected void styleChild(ISystemModel systemModel, IClassModel child) {
        addCommonDecoratorStyle(systemModel, child);
        systemModel.addClassModelSteretypes(child, this.config.getChildStereotype());
    }

    @Override
    protected void styleChildParentRelationship(ISystemModel systemModel, IClassModel child, IClassModel parent) {
        systemModel.addStyleToRelation(child, parent, RelationHasA.REL_KEY, "xlabel", this.config.getChildParentRelationshipLabel());
    }

    @Override
    protected void updateRelatedClasses(ISystemModel systemModel, IClassModel decoratorClass) {
        systemModel.getClasses().stream()
                .filter((classModel) -> decoratorClass.equals(classModel.getSuperClass()))
                .forEach((classModel) -> {
                    addCommonDecoratorStyle(systemModel, classModel);
                    systemModel.addClassModelSteretypes(classModel, this.config.getChildStereotype());
                });
    }
}
