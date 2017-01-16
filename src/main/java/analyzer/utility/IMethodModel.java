package analyzer.utility;

import utility.MethodType;
import utility.Modifier;

import java.util.List;

/**
 * An Interface of the Method Models.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IMethodModel {

    /**
     * Returns the name of the Method.
     *
     * @return Name of the Method.
     */
    String getName();

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

    /**
     * Returns the Method Type (Abstract, Constructor, Static, Static
     * Initializer, Method).
     *
     * @return Method Type.
     */
    MethodType getMethodType();

    /**
     * @return true if this method is static
     */
    boolean isStatic();

    /**
     * Returns true if the Method is final.
     *
     * @return true if Final.
     */
    boolean isFinal();

    /**
     * Returns the return type Type Model of the Method.
     *
     * @return Type Model of the Return Type.
     */
    ITypeModel getReturnType();

    /**
     * Returns an Iterable of Type Models of the arguments of the Method.
     *
     * @return Iterable of Type Model of Arguments.
     */
    List<? extends ITypeModel> getArguments();

    /**
     * @return list of instruction in this method for processing
     */
    List<? extends IInstructionModel> getInstructions();

}
