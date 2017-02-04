package analyzer.utility;

import java.util.Collection;

/**
 * The instruction model that can encapsulate all java byteCode. Right now only
 * method calls and field accesses are supported
 * 
 * @author zhang
 *
 */
public interface IInstructionModel {

    /**
     * 
     * @return the collection of types this instruction depends on
     */
    Collection<? extends ITypeModel> getDependentTypes();

    /**
     * 
     * @return the component it accessed
     */
    IClassComponent getAccessComponent();
}
