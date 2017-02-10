package analyzer.decorator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import analyzer.utility.IClassModel;
import analyzer.utility.IMethodModel;
import config.IConfiguration;
import utility.MethodType;

/**
 * A Bad Decorator Analyzer. It will highlight classes that are likely
 * decorators that are incomplete in yellow (default).
 * <p>
 * Created by lamd on 2/2/2017.
 */
public class BadDecoratorAnalyzer extends DecoratorTemplate {
    @Override
    protected boolean methodsMapped(IClassModel child, IClassModel composedClazz, IClassModel parent) {
        Collection<? extends IMethodModel> parentMethods = parent.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD).collect(Collectors.toList());

        Set<IMethodModel> decoratedMethods = new HashSet<>();

        child.getMethods().stream().filter(
                (method) -> method.getMethodType() == MethodType.METHOD && isDecoratedMethod(method, parentMethods))
                .forEach(decoratedMethods::add);

        return decoratedMethods.size() != parentMethods.size();
    }

    @Override
    protected IAdapterDecoratorConfiguration setupConfig(IConfiguration config) {
        return config.createConfiguration(BadDecoratorConfiguration.class);
    }

    @Override
    protected boolean detectPattern(IClassModel clazz, IClassModel composedClazz, IClassModel parent) {
        return composedClazz.equals(parent);
    }

}
