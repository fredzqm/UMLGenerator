package analyzer.decorator;

import analyzer.relationParser.RelationHasA;
import analyzer.utility.*;
import utility.MethodType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lamd on 2/2/2017.
 */
public class DecoratorAnalyzer extends AbstractAdapterDecoratorTemplate {
    private boolean hasParentAsField(IClassModel child, IClassModel parent) {
        Collection<? extends IFieldModel> fields = child.getFields();

        for (IFieldModel field : fields) {
            if (field.getFieldType().equals(parent)) {
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
                .filter((method) -> method.getMethodType() == MethodType.METHOD).collect(Collectors.toList());

        Set<IMethodModel> decoratedMethods = new HashSet<>();
        for (IMethodModel method : child.getMethods()) {
            if (method.getMethodType() == MethodType.METHOD && isDecoratedMethod(method, parentMethods) && method.getAccessedFields().contains(parent)) {
                decoratedMethods.add(method);
            }
        }

        return decoratedMethods.size() == parentMethods.size();
    }

    @Override
    protected boolean evaluateParent(IClassModel child, IClassModel parent) {
        return hasParentAsField(child, parent) && hasParentAsConstructorArgument(child, parent)
                && hasParentMethodMapped(child, parent);

    }

    @Override
    protected IClassModel createParentClassModel(IClassModel validatedParent) {
        final String PARENT_NODE_STYLE = "style=\"filled\" fillcolor=\"green\"";
        final String PARENT_STEREOTYPE = "component";
        return new ClassModelStyleDecorator(validatedParent, PARENT_NODE_STYLE, PARENT_STEREOTYPE);
    }

    @Override
    protected IClassModel createChildClassModel(IClassModel child) {
        final String CHILD_NODE_STYLE = "style=\"filled\" fillcolor=\"green\"";
        final String CHILD_STEREOTYPE = "decorator";
        return new ClassModelStyleDecorator(child, CHILD_NODE_STYLE, CHILD_STEREOTYPE);
    }

    @Override
    protected IRelationInfo createRelation(IRelationInfo info) {
        final String RELATION_EDGE_STYLE = "xlabel=\"\\<\\<decorates\\>\\>\"";
        return (info instanceof RelationHasA) ? new RelationStyleDecorator(info, RELATION_EDGE_STYLE) : info;
    }

    @Override
    protected Set<IClassModel> updateRelatedClasses(Set<IClassModel> classes, Map<IClassModel, Collection<IClassModel>> updateMap, IClassModel clazz) {
        Set<IClassModel> updatedClasses = new HashSet<>();
        updatedClasses.addAll(classes);

        for (IClassModel model : updateMap.keySet()) {
            if (!updatedClasses.contains(model)) {
                updatedClasses.add(new ClassModelStyleDecorator(model, "style=\"filled\" fillcolor=\"green\"", "decorator"));
            }
        }

        return updatedClasses;
    }
}
