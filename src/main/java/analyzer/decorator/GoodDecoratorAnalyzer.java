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
 * A Good Decorator Pattern Analyzer. It will highlight in green all suspected decorator classes in green (default).
 * <p>
 * Created by lamd on 2/2/2017.
 */
public class GoodDecoratorAnalyzer extends DecoratorTemplate {
    private boolean hasParentMethodMapped(IClassModel child, IClassModel parent) {
        Collection<? extends IMethodModel> parentMethods = parent.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD).collect(Collectors.toList());

        Set<IMethodModel> decoratedMethods = new HashSet<>();
        child.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD && isDecoratedMethod(method, parentMethods) && isParentFieldCalled(parent, method))
                .forEach(decoratedMethods::add);

        return decoratedMethods.size() == parentMethods.size();
    }

    @Override
    protected IAdapterDecoratorConfiguration setupConfig(IConfiguration config) {
        GoodDecoratorConfiguration updatedConfig = config.createConfiguration(GoodDecoratorConfiguration.class);
        updatedConfig.setIfMissing(GoodDecoratorConfiguration.FILL_COLOR, "green");
        updatedConfig.setIfMissing(GoodDecoratorConfiguration.PARENT_STEREOTYPE, "component");
        updatedConfig.setIfMissing(GoodDecoratorConfiguration.CHILD_STEREOTYPE, "decorator");
        updatedConfig.setIfMissing(GoodDecoratorConfiguration.CHILD_PARENT_RELATIONSHIP_LABEL, "decorates");
        return updatedConfig;
    }

    @Override
    protected boolean detectPattern(IClassModel child, IClassModel parent) {
        return hasParentAsField(child, parent) && hasParentAsConstructorArgument(child, parent)
                && hasParentMethodMapped(child, parent);
    }
}
