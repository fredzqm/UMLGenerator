package analyzer.decorator;

import analyzer.relationParser.RelationHasA;
import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;
import analyzer.utility.ISystemModel;
import model.Signature;
import utility.MethodType;

import java.util.Collection;

/**
 * A Decorator Abstract class that contains basic utility methods used by both
 * Good and Bad Decorator Analyzers.
 * <p>
 * Created by lamd on 2/7/2017.
 */
public abstract class DecoratorTemplate extends AdapterDecoratorTemplate {
    protected boolean hasParentAsField(IClassModel child, IClassModel parent) {
        return child.getFields().stream()
                .anyMatch((field) -> field.getFieldType().equals(parent));
    }

    protected boolean hasParentAsConstructorArgument(IClassModel child, IClassModel parent) {
        return child.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.CONSTRUCTOR)
                .flatMap((method) -> method.getArguments().stream())
                .anyMatch(parent::equals);
    }

    protected boolean isDecoratedMethod(IMethodModel method, Collection<? extends IMethodModel> parentMethods) {
        Signature methodSignature = method.getSignature();
        return parentMethods.stream()
                .map(IMethodModel::getSignature)
                .anyMatch((parentMethodSignature) -> parentMethodSignature.equals(methodSignature));
    }

    protected boolean isParentFieldCalled(IClassModel parent, IMethodModel method) {
        return method.getAccessedFields().stream()
                .map(IFieldModel::getFieldType)
                .anyMatch((type) -> type.equals(parent));
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
        systemModel.addStyleToRelation(child, parent, RelationHasA.REL_KEY, "xlabel",
                this.config.getChildParentRelationshipLabel());
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
