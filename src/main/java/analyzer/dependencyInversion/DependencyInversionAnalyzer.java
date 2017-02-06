package analyzer.dependencyInversion;

import analyzer.utility.IAnalyzer;
import analyzer.utility.IClassComponent;
import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IInstructionModel;
import analyzer.utility.IMethodModel;
import analyzer.utility.ISystemModel;
import analyzer.utility.ITypeModel;
import config.IConfiguration;
import utility.ClassType;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lamd on 1/14/2017.
 */
public class DependencyInversionAnalyzer implements IAnalyzer {

    private DependencyInversionConfiguration config;

    @Override
    public void analyze(ISystemModel systemModel, IConfiguration iConfig) {
        this.config = iConfig.createConfiguration(DependencyInversionConfiguration.class);

        for (IClassModel clazz : systemModel.getClasses()) {
            if (violate(clazz))
                systemModel.addClassModelStyle(clazz, "color", config.getColor());
        }
    }
    
    /**
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
