package analyzer.singleton;

import java.util.ArrayList;
import java.util.Collection;

import analyzer.utility.IClassModel;
import analyzer.utility.IMethodModel;
import analyzer.utility.ISystemModel;
import analyzer.utility.ISystemModelFilter;
import utility.MethodType;
import utility.Modifier;

public class SingletonSystemModel extends ISystemModelFilter {

    public SingletonSystemModel(ISystemModel systemModel) {
        super(systemModel);
    }

    @Override
    public Collection<? extends IClassModel> getClasses() {
        Collection<IClassModel> classes = new ArrayList<>();

        for (IClassModel clazz : classes) {
            clazz = checkSingleton(clazz);
            classes.add(clazz);
        }
        return classes;
    }

    private IClassModel checkSingleton(IClassModel clazz) {
        Collection<? extends IMethodModel> methods = clazz.getMethods();
        for (IMethodModel method : methods) {
            if (method.getMethodType() == MethodType.CONSTRUCTOR){
                if (method.getModifier() != Modifier.PRIVATE)
                    return clazz;
            }
        }
        
        return null;
    }

}
