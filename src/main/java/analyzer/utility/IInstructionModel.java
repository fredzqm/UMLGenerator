package analyzer.utility;

import java.util.Collection;

public interface IInstructionModel {

    Collection<? extends ITypeModel> getDependentClass();

}
