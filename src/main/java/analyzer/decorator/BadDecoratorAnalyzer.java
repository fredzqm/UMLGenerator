package analyzer.decorator;

import analyzer.utility.IClassModel;
import config.IConfiguration;

/**
 * A Bad Decorator Analyzer. It will highlight classes that are likely decorators that are incomplete in yellow.
 * <p>
 * Created by lamd on 2/2/2017.
 */
public class BadDecoratorAnalyzer extends DecoratorTemplate {
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
