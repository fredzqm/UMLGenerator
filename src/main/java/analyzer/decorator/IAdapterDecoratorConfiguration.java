package analyzer.decorator;

import config.Configurable;

/**
 * Created by lamd on 2/7/2017.
 */
public interface IAdapterDecoratorConfiguration extends Configurable {
    String getFillColor();

    String getParentStereotype();

    String getChildStereotype();

    String getChildParentRelationshipLabel();
}
