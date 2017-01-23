package model;

/**
 * everything model needs to know to parse the model
 *
 * @author zhang
 */
public interface IModelConfiguration {
    /**
     * Return the name of classes that the model needs to analyzer
     */
    Iterable<String> getClasses();

    /**
     * @return true if the Model should recursively explore related classes
     */
    boolean isRecursive();

    /**
     * Returns an Iterable of Black list class names.
     *
     * @return Iterable of black list class names.
     */
    Iterable<String> getBlackList();

    /**
     * Returns the boolean value of the verbose flag.
     *
     * @return true if the verbose flag is on.
     */
    boolean isVerbose();

    /**
     * Returns the boolean value of the synthetic flag.
     *
     * @return true if the synthetic flag is on.
     */
    boolean filterSynthetic();
}
