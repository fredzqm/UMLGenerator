package analyzer.utility;

import java.util.Collection;
import java.util.List;

import utility.MethodType;

/**
 * An Interface of the Method Models.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IMethodModel extends IClassComponent {

    /**
     * Returns the Method Type (Abstract, Constructor, Static, Static
     * Initializer, Method).
     *
     * @return Method Type.
     */
    MethodType getMethodType();

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

    /**
     * @return the list of fields this method accessed
     */
    Collection<? extends IFieldModel> getAccessedFields();

    /**
     * @return the list of methods this method called
     */
    Collection<? extends IMethodModel> getCalledMethods();

}
