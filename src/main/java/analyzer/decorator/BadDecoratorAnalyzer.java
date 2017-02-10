package analyzer.decorator;

import analyzer.utility.IClassModel;
import analyzer.utility.IMethodModel;
import config.IConfiguration;
import utility.MethodType;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A Bad Decorator Analyzer. It will highlight classes that are likely
 * decorators that are incomplete in yellow (default).
 * <p>
 * Created by lamd on 2/2/2017.
 */
public class BadDecoratorAnalyzer extends DecoratorTemplate {
    @Override
    protected Set<IMethodModel> getMappedMethods(IClassModel child, IClassModel composedClass, IClassModel parent) {
        Collection<? extends IMethodModel> overridedMethods = parent.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD).collect(Collectors.toList());

        Set<IMethodModel> overridingMethods = child.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD && isDecoratedMethod(method, overridedMethods))
                .collect(Collectors.toSet());

        if (overridingMethods.size() == overridedMethods.size()) {
            return null;
        }
        return overridingMethods;
    }

    @Override
    protected IAdapterDecoratorConfiguration setupConfig(IConfiguration config) {
        return config.createConfiguration(BadDecoratorConfiguration.class);
    }

    @Override
    protected boolean detectPattern(IClassModel clazz, IClassModel composedClass, IClassModel parent,
                                    Set<IMethodModel> overridingMethods) {
        return composedClass.equals(parent);
    }
}
