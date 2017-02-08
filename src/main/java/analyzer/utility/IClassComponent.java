package analyzer.utility;

import utility.Modifier;

/**
 * Representing ClassComponent like methods and fields. It contains some basic
 * metadata getters.
 *
 * @author zhang
 */
public interface IClassComponent {
    /**
     * Returns the String of this component
     *
     * @return Class Name.
     */
    String getName();

    /**
     * @return true if this class is a final class
     */
    boolean isFinal();

    /**
     * @return true if this class is static
     */
    boolean isStatic();

    /**
     * @return true if this class is synthetic
     */
    boolean isSynthetic();

    /**
     * @return the class it belongs to
     */
    IClassModel getBelongTo();

    /**
     * Returns the access Modifier of this method.
     *
     * @return Access Modifier.
     */
    Modifier getModifier();
}
