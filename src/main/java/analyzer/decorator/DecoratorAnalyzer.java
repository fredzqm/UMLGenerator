package analyzer.decorator;

import analyzer.relationParser.RelationHasA;
import analyzer.utility.*;
import utility.MethodType;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by lamd on 2/2/2017.
 */
public class DecoratorAnalyzer extends AbstractAdapterDecoratorTemplate {

    private boolean hasParentAsField(IClassModel child, IClassModel parent) {
        Collection<? extends IFieldModel> fields = child.getFields();

        for (IFieldModel field : fields) {
            // TODO: Fred how do I compare field types with the superClasses type?
            if (field.getFieldType().getClassModel().equals(parent)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasParentAsConstructorArgument(IClassModel child, IClassModel parent) {
        Collection<? extends IMethodModel> methods = child.getMethods();

        List<? extends ITypeModel> arguments;
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

    private boolean hasParentMethodMapped(IClassModel child, IClassModel parent) {
        Collection<? extends IMethodModel> parentMethods = parent.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD)
                .collect(Collectors.toList());

        Set<IMethodModel> decoratedMethods = new HashSet<>();
        for (IMethodModel method : child.getMethods()) {
            if (method.getMethodType() == MethodType.METHOD && isDecoratedMethod(method, parentMethods)) {
                decoratedMethods.add(method);
            }
        }

        return decoratedMethods.size() == parentMethods.size();
    }

    @Override
    protected boolean evaluateParent(IClassModel child, IClassModel parent) {
        if (hasParentAsField(child, parent)
                && hasParentAsConstructorArgument(child, parent)
                && hasParentMethodMapped(child, parent)) {
            return true;
        }

        return false;
    }

    @Override
    protected IClassModel createParentClassModel(IClassModel validatedParent) {
        return new ClassModelStyleDecorator(validatedParent, "style=\"filled\" fillcolor=\"green\"", "component");
    }

    @Override
    protected IClassModel createChildClassModel(IClassModel child) {
        return new ClassModelStyleDecorator(child, "style=\"filled\" fillcolor=\"green\"", "decorator");
    }

    @Override
    protected IRelationInfo createRelation(IRelationInfo info) {
        return (info instanceof RelationHasA) ? new RelationStyleDecorator(info, "xlabel=\"\\<\\<decorates\\>\\>\"") : info;
    }
}
