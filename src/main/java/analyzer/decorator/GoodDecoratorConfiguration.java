package analyzer.decorator;

import config.IConfiguration;

/**
 * A Configurable for Good Decorator Analyzers.
 * <p>
 * Created by lamd on 1/23/2017.
 */
public class GoodDecoratorConfiguration implements IAdapterDecoratorConfiguration {
    public static final String CONFIG_PATH = "goodDecorator.";
    public static final String FILL_COLOR = CONFIG_PATH + "fillColor";
    public static final String PARENT_STEREOTYPE = CONFIG_PATH + "parentStereotype";
    public static final String CHILD_STEREOTYPE = CONFIG_PATH + "childStereotype";
    public static final String CHILD_PARENT_RELATIONSHIP_LABEL = CONFIG_PATH + "childParentRelationshipLabel";


    private IConfiguration config;

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
    }

    public void setIfMissing(String key, String value) {
        this.config.setIfMissing(key, value);
    }

    @Override
    public String getFillColor() {
        return this.config.getValue(GoodDecoratorConfiguration.FILL_COLOR);
    }

    @Override
    public String getParentStereotype() {
        return this.config.getValue(GoodDecoratorConfiguration.PARENT_STEREOTYPE);
    }

    @Override
    public String getChildStereotype() {
        return this.config.getValue(GoodDecoratorConfiguration.CHILD_STEREOTYPE);
    }

    @Override
    public String getChildParentRelationshipLabel() {
        return this.config.getValue(GoodDecoratorConfiguration.CHILD_PARENT_RELATIONSHIP_LABEL);
    }
}