package analyzer.syntheticFilter;

import java.util.ArrayList;
import java.util.Collection;

import analyzer.utility.IClassModel;
import analyzer.utility.IClassModelFilter;
import analyzer.utility.IMethodModel;

public class SyntheticFilterClassModel extends IClassModelFilter {

    SyntheticFilterClassModel(IClassModel classModel) {
        super(classModel);
    }

    @Override
    public Collection<? extends IMethodModel> getMethods() {
        Collection<IMethodModel> methods = new ArrayList<>();
        for (IMethodModel method : super.getMethods()) {
            if (!isSynthetic(method.getName()))
                methods.add(method);
        }
        return methods;
    }

    private static boolean isSynthetic(String methodName) {
        return methodName.contains("lamgda$");
    }
}
