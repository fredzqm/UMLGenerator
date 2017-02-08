package analyzer.decorator;

import config.IConfiguration;

/**
 * A Configurable for Good Decorator Analyzers.
 * <p>
 * Created by lamd on 1/23/2017.
 */
public class BadDecoratorConfiguration implements IAdapterDecoratorConfiguration {
    public static final String CONFIG_PATH = "badDecorator.";
    public static final String FILL_COLOR = CONFIG_PATH + "fillColor";
    public static final String PARENT_STEREOTYPE = CONFIG_PATH + "parentStereotype";
    public static final String CHILD_STEREOTYPE = CONFIG_PATH + "childStereotype";
    public static final String CHILD_PARENT_RELATIONSHIP_LABEL = CONFIG_PATH + "childParentRelationshipLabel";

    private IConfiguration config;

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        this.config.setIfMissing(BadDecoratorConfiguration.FILL_COLOR, "yellow");
        this.config.setIfMissing(BadDecoratorConfiguration.PARENT_STEREOTYPE, "component");
        this.config.setIfMissing(BadDecoratorConfiguration.CHILD_STEREOTYPE, "decorator");
        this.config.setIfMissing(BadDecoratorConfiguration.CHILD_PARENT_RELATIONSHIP_LABEL, "decorates");
    }

    public void setIfMissing(String key, String value) {
        this.config.setIfMissing(key, value);
    }

    @Override
    public String getFillColor() {
        return this.config.getValue(BadDecoratorConfiguration.FILL_COLOR);
    }

    @Override
    public String getParentStereotype() {
        return this.config.getValue(BadDecoratorConfiguration.PARENT_STEREOTYPE);
    }

    @Override
    public String getChildStereotype() {
        return this.config.getValue(BadDecoratorConfiguration.CHILD_STEREOTYPE);
    }

    @Override
    public String getChildParentRelationshipLabel() {
        return this.config.getValue(BadDecoratorConfiguration.CHILD_PARENT_RELATIONSHIP_LABEL);
    }
}