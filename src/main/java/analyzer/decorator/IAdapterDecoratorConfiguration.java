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
     * Returns the Parent Stereotype label that meets the pattern analyzer class.
     *
     * @return String of parent stereotype label.
     */
    String getParentStereotype();

    /**
     * Returns the Child Stereotype label that meets the pattern analyzer class.
     *
     * @return String of the child stereotype label.
     */
    String getChildStereotype();

    /**
     * Returns the ChildParent Relation label.
     *
     * @return String of Relation Label.
     */
    String getChildParentRelationshipLabel();

    /**
     * Returns the Related Classes Stereotype label that meets the pattern analyzer class
     *
     * @return String of the Related Classes stereotype label
     */
    String getRelatedClassStereotype();
}
