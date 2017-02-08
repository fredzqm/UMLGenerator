package analyzer.syntheticFilter;

import analyzer.utility.IClassModel;
import analyzer.utility.IClassModelFilter;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;

import java.util.Collection;
import java.util.LinkedList;

public class SyntheticFilterClassModel extends IClassModelFilter {
    SyntheticFilterClassModel(IClassModel classModel) {
        super(classModel);
    }

    @Override
    public Collection<? extends IMethodModel> getMethods() {
        Collection<IMethodModel> methods = new LinkedList<>();
        super.getMethods().stream()
                .filter((method) -> !method.isSynthetic())
                .forEach(methods::add);

        return methods;
    }

    @Override
    public Collection<? extends IFieldModel> getFields() {
        Collection<IFieldModel> fields = new LinkedList<>();
        super.getFields().stream()
                .filter((field) -> !field.isSynthetic())
                .forEach(fields::add);

        return fields;
    }

}
