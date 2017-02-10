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
    public static final String RELATED_CLASS_STEREOTYPE = CONFIG_PATH + "relatedClassStereotype";

    private IConfiguration config;

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        this.config.setIfMissing(GoodDecoratorConfiguration.FILL_COLOR, "green");
        this.config.setIfMissing(GoodDecoratorConfiguration.PARENT_STEREOTYPE, "component");
        this.config.setIfMissing(GoodDecoratorConfiguration.CHILD_STEREOTYPE, "decorator");
        this.config.setIfMissing(GoodDecoratorConfiguration.CHILD_PARENT_RELATIONSHIP_LABEL, "decorates");
        this.config.setIfMissing(GoodDecoratorConfiguration.RELATED_CLASS_STEREOTYPE, "decorator");
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

    @Override
    public String getRelatedClassStereotype() {
        return this.config.getValue(GoodDecoratorConfiguration.RELATED_CLASS_STEREOTYPE);
    }
}