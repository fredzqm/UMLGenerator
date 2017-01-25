package analyzer.syntheticFilter;

import analyzer.utility.IClassModel;
import analyzer.utility.IClassModelFilter;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;

import java.util.ArrayList;
import java.util.Collection;

public class SyntheticFilterClassModel extends IClassModelFilter {

    SyntheticFilterClassModel(IClassModel classModel) {
        super(classModel);
    }

    @Override
    public Collection<? extends IMethodModel> getMethods() {
        Collection<IMethodModel> methods = new ArrayList<>();
        super.getMethods().forEach((method) -> {
            if (!method.isSynthetic()) {
                methods.add(method);
            }
        });
        return methods;
    }

    @Override
    public Collection<? extends IFieldModel> getFields() {
        Collection<IFieldModel> fields = new ArrayList<>();
        super.getFields().forEach((field) -> {
            if (!field.isSynthetic()) {
                fields.add(field);
            }
        });
        return fields;
    }

}
