package analyzer.singleton;

import analyzer.utility.*;
import config.IConfiguration;
import utility.MethodType;
import utility.Modifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SingletonAnalyzer implements IAnalyzer {
    private SingletonConfiguration config;

    @Override
    public ISystemModel analyze(ISystemModel systemModel, IConfiguration config) {
        this.config = config.createConfiguration(SingletonConfiguration.class);
        return new ProcessedSystemModel(getClasses(systemModel.getClasses()), systemModel.getRelations());
    }

    public Set<? extends IClassModel> getClasses(Set<? extends IClassModel> classList) {
        Set<IClassModel> classes = new HashSet<>();
        for (IClassModel clazz : classList) {
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
        return new ClassModelStyleDecorator(clazz, format, "Singleton");
    }

}
