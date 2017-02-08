package analyzer.decorator;

import config.Configurable;

/**
 * A Common AdapterDecorator Configuration Interface.
 * <p>
 * Created by lamd on 2/7/2017.
 */
public interface IAdapterDecoratorConfiguration extends Configurable {
    /**
     * Returns the fill color of the Class that meets the pattern analyser.
     *
     * @return String of the fill color.
     */
    String getFillColor();

    /**
     * Returns the Parent Sterotype label that meets the pattern analyzer class.
     *
     * @return String of parent sterotype label.
     */
    String getParentStereotype();

    /**
     * Returns the Child Sterotype label that meets the pattern analyzer class.
     *
     * @return String of the child stereotype label.
     */
    String getChildStereotype();

    /**
     * Returns the ChildParent Relation lable.
     *
     * @return String of Relation Label.
     */
    String getChildParentRelationshipLabel();
}
