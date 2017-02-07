package analyzer.decorator;

import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;
import analyzer.utility.ITypeModel;
import utility.MethodType;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by lamd on 2/7/2017.
 */
public abstract class DecoratorTemplate extends AdapterDecoratorTemplate {
    protected boolean hasParentAsField(IClassModel child, IClassModel parent) {
        Collection<? extends IFieldModel> fields = child.getFields();
        for (IFieldModel field : fields) {
            if (field.getFieldType().equals(parent)) {
                return true;
            }
        }
        return false;
    }

    protected boolean hasParentAsConstructorArgument(IClassModel child, IClassModel parent) {
        List<? extends ITypeModel> arguments;
        Collection<? extends IMethodModel> methods = child.getMethods();
        for (IMethodModel method : methods) {
            if (method.getMethodType() == MethodType.CONSTRUCTOR) {
                arguments = method.getArguments();
                for (ITypeModel type : arguments) {
                    if (type.getClassModel().equals(parent)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isDecoratedMethod(IMethodModel method, Collection<? extends IMethodModel> parentMethods) {
        for (IMethodModel parentMethod : parentMethods) {
            if (parentMethod.getSignature().equals(method.getSignature())) {
                return true;
            }
        }
        return false;
    }

    private boolean isParentFieldCalled(IClassModel parent, IMethodModel method) {
        for (IFieldModel field : method.getAccessedFields()) {
            if (field.getFieldType().equals(parent)) {
                return true;
            }
        }
        return false;
    }

    protected boolean hasParentMethodMapped(IClassModel child, IClassModel parent) {
        Collection<? extends IMethodModel> parentMethods = parent.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD).collect(Collectors.toList());

        Set<IMethodModel> decoratedMethods = new HashSet<>();
        child.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD && isDecoratedMethod(method, parentMethods) && isParentFieldCalled(parent, method))
                .forEach(decoratedMethods::add);

        return decoratedMethods.size() == parentMethods.size();
    }
}
