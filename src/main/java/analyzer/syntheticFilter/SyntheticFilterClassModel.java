package analyzer.syntheticFilter;

import java.util.ArrayList;
import java.util.Collection;

import analyzer.utility.IClassModel;
import analyzer.utility.IClassModelFilter;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;

public class SyntheticFilterClassModel extends IClassModelFilter {

    SyntheticFilterClassModel(IClassModel classModel) {
        super(classModel);
    }

    @Override
    public Collection<? extends IMethodModel> getMethods() {
        Collection<IMethodModel> methods = new ArrayList<>();
        for (IMethodModel method : super.getMethods()) {
            if (!method.isSynthetic())
                methods.add(method);
        }
        return methods;
    }

    @Override
    public Collection<? extends IFieldModel> getFields() {
        Collection<IFieldModel> fields = new ArrayList<>();
        for (IFieldModel field : super.getFields()) {
            if (!field.isSynthetic())
                fields.add(field);
        }
        return fields;
    }

}
