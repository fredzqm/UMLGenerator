package analyzer;

import utility.MethodType;
import utility.Modifier;

/**
 * An Interface of the Method Models.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IMethodModel {

    /**
     * Returns the access Modifier of this method.
     *
     * @return Access Modifier.
     */
    Modifier getModifier();

    /**
     * Returns the Method Type (Abstract, Contructor, Static, Static
     * Initializer, Method).
     *
     * @return Method Type.
     */
    MethodType getMethodType();

    /**
     * Returns true if the Method is final.
     *
     * @return true if Final.
     */
    boolean isFinal();

    /**
     * Returns the name of the Method.
     *
     * @return Name of the Method.
     */
    String getName();

    /**
     * Returns the return type Type Model of the Method.
     *
     * @return Type Model of the Return Type.
     */
    String getReturnTypeName();

    /**
     * Returns an Iterable of Type Models of the arguments of the Method.
     *
     * @return Iterable of Type Model of Arguments.
     */
    Iterable<? extends String> getArgumentTypeNames();

}
