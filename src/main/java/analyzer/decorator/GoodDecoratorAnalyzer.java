package analyzer.decorator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;
import config.IConfiguration;
import utility.MethodType;

/**
 * A Good Decorator Pattern Analyzer. It will highlight in green all suspected
 * decorator classes in green (default).
 * <p>
 * Created by lamd on 2/2/2017.
 */
public class GoodDecoratorAnalyzer extends DecoratorTemplate {
    private boolean hasParentMethodMapped(IClassModel child, IClassModel parent) {
        Collection<? extends IMethodModel> parentMethods = parent.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD).collect(Collectors.toList());

        Set<IMethodModel> decoratedMethods = new HashSet<>();
        child.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD
                        && isDecoratedMethod(method, parentMethods) && isFieldCalled(parent, method))
                .forEach(decoratedMethods::add);
        return decoratedMethods.size() == parentMethods.size();
    }

    protected boolean isFieldCalled(IClassModel parent, IMethodModel method) {
        return method.getAccessedFields().stream().map(IFieldModel::getFieldType)
                .anyMatch((type) -> type.equals(parent));
    }

    @Override
    protected IAdapterDecoratorConfiguration setupConfig(IConfiguration config) {
        return config.createConfiguration(GoodDecoratorConfiguration.class);
    }

    @Override
    protected boolean detectPattern(IClassModel clazz, IClassModel composedClazz, IClassModel parent) {
        return composedClazz.equals(parent) && hasParentMethodMapped(clazz, parent);
    }

}
