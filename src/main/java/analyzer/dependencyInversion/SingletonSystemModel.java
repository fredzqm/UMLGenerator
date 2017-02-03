package analyzer.dependencyInversion;

import analyzer.utility.*;
import utility.MethodType;
import utility.Modifier;

import java.util.ArrayList;
import java.util.Collection;

public class SingletonSystemModel extends ISystemModelFilter {
    private final DependencyInversionConfiguration config;

    SingletonSystemModel(ISystemModel systemModel, DependencyInversionConfiguration config) {
        super(systemModel);
        this.config = config;
    }

    @Override
    public Collection<? extends IClassModel> getClasses() {
        Collection<IClassModel> classes = new ArrayList<>();
        for (IClassModel clazz : super.getClasses()) {
            classes.add(checkSingleton(clazz));
            classes.add(checkSingleton(clazz));
        }
        return classes;
    }

    /**
     * We use the following rules to determine if a class is a singleton:
     * <p>
     * 1. It has one private constructor 2. It has one static field of itself 3.
     * There is a nonprivate getter for this singleton 4. Either the nonprivate
     * getter or static initializer
     *
     * @param clazz
     * @return
     */
    private IClassModel checkSingleton(IClassModel clazz) {
        // check all methods to make sure there is only private constructor
        Collection<? extends IMethodModel> methods = clazz.getMethods();
        IMethodModel privateConstructor = null;
        for (IMethodModel method : methods) {
            if (method.getMethodType() == MethodType.CONSTRUCTOR) {
                if (method.getModifier() == Modifier.PRIVATE && privateConstructor == null) {
                    privateConstructor = method;
                } else {
                    // non private constructor or multiple constructor
                    return clazz;
                }
            }
        }

        // look for the static instance of singleton
        Collection<? extends IFieldModel> fields = clazz.getFields();
        IFieldModel staticSingletonField = null;
        for (IFieldModel field : fields) {
            if (clazz.equals(field.getFieldType().getClassModel())) {
                if (field.isStatic() && field.getModifier() == Modifier.PRIVATE && staticSingletonField == null) {
                    staticSingletonField = field;
                } else {
                    // more than two staticSingletonField
                    return clazz;
                }
            }
        }
        // no static field
        if (staticSingletonField == null)
            return clazz;

        IMethodModel staticGetInstanceMethod = null;
        for (IMethodModel method : methods) {
            if (clazz.equals(method.getReturnType())) {
                if (method.getModifier() != Modifier.PRIVATE) {
                    if (method.isStatic() && staticGetInstanceMethod == null) {
                        staticGetInstanceMethod = method;
                    } else {
                        // more than two staticGetInstanceMethod
                        return clazz;
                    }
                }
            }
        }

        String format = String.format("color=\"%s\"", this.config.getColor());
        return new ClassModelStyleDecorator(clazz, format);
    }

}
