package analyzer.dependencyInversion;

import java.util.HashSet;
import java.util.Set;

import analyzer.utility.ClassModelStyleDecorator;
import analyzer.utility.IClassComponent;
import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IInstructionModel;
import analyzer.utility.IMethodModel;
import analyzer.utility.ISystemModel;
import analyzer.utility.ISystemModelFilter;
import analyzer.utility.ITypeModel;
import utility.ClassType;

public class DependencyInversionSystemModel extends ISystemModelFilter {
    private final DependencyInversionConfiguration config;

    DependencyInversionSystemModel(ISystemModel systemModel, DependencyInversionConfiguration config) {
        super(systemModel);
        this.config = config;
    }

    @Override
    public Set<? extends IClassModel> getClasses() {
        Set<IClassModel> classes = new HashSet<>();
        String format = String.format("color=\"%s\"", this.config.getColor());
        for (IClassModel clazz : super.getClasses()) {
            if (violate(clazz))
                clazz = new ClassModelStyleDecorator(clazz, format);
            classes.add(clazz);
        }
        return classes;
    }

    /**
     * 
     * @param clazz
     * @return true if this class violates the dependency inversion principle
     */
    private boolean violate(IClassModel clazz) {
        for (IFieldModel field : clazz.getFields()) {
            if (violate(field.getFieldType(), clazz))
                return true;
        }
        for (IMethodModel method : clazz.getMethods()) {
            if (violate(method.getReturnType(), clazz))
                return true;
            for (ITypeModel t : method.getArguments())
                if (violate(t, clazz))
                    return true;
            for (IInstructionModel inst : method.getInstructions()) {
                // calls an static method on another class
                IClassComponent accessed = inst.getAccessComponent();
                if (accessed != null && accessed.isStatic() && !accessed.getBelongTo().equals(clazz))
                    return true;
                for (ITypeModel t : inst.getDependentTypes())
                    if (violate(t, clazz))
                        return true;
            }
        }
        return false;
    }

    private boolean violate(ITypeModel fieldType, IClassModel selfClass) {
        for (IClassModel clazz : fieldType.getDependentClass()) {
            if (clazz.getType() == ClassType.CONCRETE && !clazz.equals(selfClass) && !isInWhiteList(clazz.getName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isInWhiteList(String className) {
        for (String p : config.getWhiteList()) {
            if (className.startsWith(p))
                return true;
        }
        return false;
    }
}