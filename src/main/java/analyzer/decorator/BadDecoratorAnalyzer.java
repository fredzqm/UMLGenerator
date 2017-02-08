package analyzer.decorator;

import analyzer.utility.IClassModel;
import analyzer.utility.IMethodModel;
import config.IConfiguration;
import utility.MethodType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A Bad Decorator Analyzer. It will highlight classes that are likely decorators that are incomplete in yellow (default).
 * <p>
 * Created by lamd on 2/2/2017.
 */
public class BadDecoratorAnalyzer extends DecoratorTemplate {
    private boolean hasParentMethodMapped(IClassModel child, IClassModel parent) {
        Collection<? extends IMethodModel> parentMethods = parent.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD).collect(Collectors.toList());

        Set<IMethodModel> decoratedMethods = new HashSet<>();
        child.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD && isDecoratedMethod(method, parentMethods))
                .forEach(decoratedMethods::add);

        return decoratedMethods.size() == parentMethods.size();
    }

    @Override
    protected IAdapterDecoratorConfiguration setupConfig(IConfiguration config) {
        BadDecoratorConfiguration updatedConfig = config.createConfiguration(BadDecoratorConfiguration.class);
        updatedConfig.setIfMissing(BadDecoratorConfiguration.FILL_COLOR, "yellow");
        updatedConfig.setIfMissing(BadDecoratorConfiguration.PARENT_STEREOTYPE, "component");
        updatedConfig.setIfMissing(BadDecoratorConfiguration.CHILD_STEREOTYPE, "decorator");
        updatedConfig.setIfMissing(BadDecoratorConfiguration.CHILD_PARENT_RELATIONSHIP_LABEL, "decorates");
        return updatedConfig;
    }

    @Override
    protected boolean detectPattern(IClassModel child, IClassModel parent) {
        return hasParentAsField(child, parent) && hasParentAsConstructorArgument(child, parent)
                && !hasParentMethodMapped(child, parent);
    }
}
