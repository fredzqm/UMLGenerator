package analyzer.singleton;

import java.util.ArrayList;
import java.util.Collection;

import analyzer.utility.*;
import utility.MethodType;
import utility.Modifier;

public class SingletonSystemModel extends ISystemModelFilter {

    public SingletonSystemModel(ISystemModel systemModel) {
        super(systemModel);
    }

    @Override
    public Collection<? extends IClassModel> getClasses() {
        Collection<IClassModel> classes = new ArrayList<>();

        for (IClassModel clazz : super.getClasses()) {
            clazz = checkSingleton(clazz);
            classes.add(clazz);
        }
        return classes;
    }

    private IClassModel checkSingleton(IClassModel clazz) {
        // check all methods to make sure there is only private constructor
        Collection<? extends IMethodModel> methods = clazz.getMethods();
        for (IMethodModel method : methods) {
            if (method.getMethodType() == MethodType.CONSTRUCTOR && method.getModifier() != Modifier.PRIVATE)
                return clazz;
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
        if (staticSingletonField == null)
            return clazz;

        IMethodModel staticGetInstanceMethod = null;
        for (IMethodModel method : methods) {
            if (method.getAccessedFields().contains(staticSingletonField)) {
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
        
        return new SingletonClassModel(clazz);
    }

}
