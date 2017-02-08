package analyzer.decorator;

import analyzer.utility.IClassModel;
import config.IConfiguration;

/**
 * A Good Decorator Pattern Analyzer. It will highlight in green all suspected decorator classes in green (default).
 * <p>
 * Created by lamd on 2/2/2017.
 */
public class GoodDecoratorAnalyzer extends DecoratorTemplate {
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
