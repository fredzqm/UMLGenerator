package analyzer.adapter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import analyzer.decorator.AdapterDecoratorTemplate;
import analyzer.decorator.IAdapterDecoratorConfiguration;
import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;
import analyzer.utility.ISystemModel;
import analyzer.utility.ITypeModel;
import config.IConfiguration;
import utility.MethodType;

/**
 * An Adapter Pattern Analyzer. It will highlight in some color (defaulted to
 * red) all classes part of the Adapter pattern instance.
 * <p>
 * Created by fineral on 2/9/2017.
 */
public class AdapterDetectorAnalyzer extends AdapterDecoratorTemplate {
    private IClassModel adaptee;

    @Override
    protected IAdapterDecoratorConfiguration setupConfig(IConfiguration config) {
        return config.createConfiguration(AdapterDetectorConfiguration.class);
    }

    @Override
    protected boolean detectPattern(IClassModel adapter, IFieldModel field, IClassModel parent) {
        // first make sure all methods in the interface are adapted
        if (adapter.equals(parent))
            return false;
        Collection<? extends IMethodModel> targetMethods = parent.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD).collect(Collectors.toList());
        Set<IMethodModel> adaptedMethods = new HashSet<>();
        adapter.getMethods().stream().filter((method) -> isDecoratedMethod(method, targetMethods))
                .forEach(adaptedMethods::add);
        // not all methods are adapted
        if (adaptedMethods.size() != targetMethods.size()) {
            return false;
        }
        // second find the adaptee field
        for (IFieldModel f : adapter.getFields()) {
            ITypeModel type = f.getFieldType();
            if (type.getDimension() > 0 || type.getClassModel() == null || adapter.isSubClazzOf(type.getClassModel()))
                continue;
            if (injectedInConstructor(f.getFieldType(), adapter)
                    && usedByAllAdaptedMethods(f, adaptedMethods)) {
                adaptee = f.getFieldType().getClassModel();
                return true;
            }
        }
        return false;
    }

    private boolean usedByAllAdaptedMethods(IFieldModel field, Set<IMethodModel> adaptedMethods) {
        IClassModel type = field.getFieldType().getClassModel();
        Set<IMethodModel> methodCalledOnTheField = new HashSet<>();
        for (IMethodModel method : adaptedMethods) {
            Collection<? extends IFieldModel> fieldsUsed = method.getAccessedFields();
            if (!fieldsUsed.contains(field)) {
                return false;
            }
            Collection<? extends IMethodModel> methodAccessed = method.getCalledMethods().stream()
                    .filter((m) -> m.getBelongTo().equals(type)).collect(Collectors.toList());
            methodCalledOnTheField.addAll(methodAccessed);
        }
        return methodCalledOnTheField.size() == adaptedMethods.size();
    }

    private boolean injectedInConstructor(ITypeModel iTypeModel, IClassModel adapter) {
        Collection<? extends IMethodModel> methods = adapter.getMethods();
        for (IMethodModel method : methods) {
            if (method.getMethodType() == MethodType.CONSTRUCTOR) {
                if (method.getArguments().contains(iTypeModel))
                    return true;
            }
        }
        return false;
    }

    @Override
    protected void updateRelatedClasses(ISystemModel systemModel, IClassModel clazz, IFieldModel field,
            IClassModel parent) {
        addCommonFillColor(systemModel, adaptee);
        systemModel.addClassModelSteretypes(adaptee, this.config.getRelatedClassStereotype());
    }

    @Override
    protected void styleChildParentRelationship(ISystemModel systemModel, IClassModel child, IClassModel parent) {
        super.styleChildParentRelationship(systemModel, child, adaptee);
    }

}
