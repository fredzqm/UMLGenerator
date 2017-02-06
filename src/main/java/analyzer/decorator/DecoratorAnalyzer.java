package analyzer.decorator;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import analyzer.relationParser.RelationHasA;
import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;
import analyzer.utility.ISystemModel;
import analyzer.utility.ITypeModel;
import config.IConfiguration;
import utility.MethodType;

/**
 * Created by lamd on 2/2/2017.
 */
public class DecoratorAnalyzer extends PatternAnalyzer {

    private Map<IClassModel, Set<IClassModel>> map;

    @Override
    public void initialize(ISystemModel systemModel, IConfiguration config) {
        Set<? extends IClassModel> classes = systemModel.getClasses();
        map = new HashMap<>();
        for (IClassModel c : classes) {
            map.put(c, new HashSet<>());
        }
        for (IClassModel c : classes) {
            addSuperClassRelToMap(c, c.getSuperClass());
            for (IClassModel intf : c.getInterfaces()) {
                addSuperClassRelToMap(c, intf);
            }
        }
    }

    private void addSuperClassRelToMap(IClassModel subClass, IClassModel superClass) {
        if (map.containsKey(superClass)) {
            map.get(superClass).add(subClass);
        }
    }

    @Override
    public void acceptPossiblePattern(ISystemModel systemModel, IClassModel clazz, IClassModel composedClass,
            IClassModel superClass) {
        if (composedClass.equals(superClass) && hasParentMethodMapped(clazz, superClass)) {
            markDecorator(systemModel, clazz);
            markComponent(systemModel, superClass);
            systemModel.addStyleToRelation(clazz, superClass, new RelationHasA(), "xlabel", "decorates");
        }
    }

    private void markComponent(ISystemModel systemModel, IClassModel superClass) {
        systemModel.addClassModelStyle(superClass, "style", "filled");
        systemModel.addClassModelStyle(superClass, "fillcolor", "green");
        systemModel.addClassModelSteretypes(superClass, "Component");
    }

    private void markDecorator(ISystemModel systemModel, IClassModel decorator) {
        systemModel.addClassModelStyle(decorator, "style", "filled");
        systemModel.addClassModelStyle(decorator, "fillcolor", "green");
        systemModel.addClassModelSteretypes(decorator, "Decorator");
        for (IClassModel subClass : map.get(decorator)) {
            markDecorator(systemModel, subClass);
        }
    }

    
    
    
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
            if (method.getMethodType() == MethodType.METHOD && isDecoratedMethod(method, parentMethods)) {
                decoratedMethods.add(method);
            }
        }

        return decoratedMethods.size() == parentMethods.size();
    }

    protected boolean evaluateParent(IClassModel child, IClassModel parent) {
        return hasParentAsField(child, parent) && hasParentAsConstructorArgument(child, parent)
                && hasParentMethodMapped(child, parent);

    }

}
