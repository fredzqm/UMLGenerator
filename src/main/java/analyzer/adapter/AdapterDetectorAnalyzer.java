package analyzer.adapter;

import analyzer.decorator.AdapterDecoratorTemplate;
import analyzer.decorator.IAdapterDecoratorConfiguration;
import analyzer.utility.*;
import config.IConfiguration;
import utility.MethodType;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An Adapter Pattern Analyzer. It will highlight in some color (defaulted to red) all classes part of the Adapter pattern instance.
 * <p>
 * Created by fineral on 2/9/2017.
 */
public class AdapterDetectorAnalyzer extends AdapterDecoratorTemplate {
    private Set<IFieldModel> active = new HashSet<>();
    private IClassModel adaptee;

    @Override
    protected void updateRelatedClasses(ISystemModel systemModel, IClassModel clazz) {
        addCommonFillColor(systemModel, adaptee);
        systemModel.addClassModelSteretypes(adaptee, this.config.getRelatedClassStereotype());
    }

    @Override
    protected IAdapterDecoratorConfiguration setupConfig(IConfiguration config) {
        return config.createConfiguration(AdapterDetectorConfiguration.class);
    }

    @Override
    protected boolean detectPattern(IClassModel adapter, IClassModel target) {
        return getConstructed(adapter, target) && getMethodFields(adapter, target) && hasSingleField();
    }
    
    @Override
    protected void styleChildParentRelationship(ISystemModel systemModel, IClassModel child, IClassModel parent) {
    	super.styleChildParentRelationship(systemModel, child, adaptee);
    }

    /**
     * Detects if there is a single field after trying to determine if a class is adapting another
     *
     * @return true - if there is a single field
     * false - if otherwise
     */
    private boolean hasSingleField() {
        if(this.active.size() == 1){
        	adaptee = this.active.iterator().next().getFieldType().getClassModel();
        	return true;
        }
        return false;
    }

    /**
     * Determine if the adapter contains the correct amount of adapted methods from the target, it also finds all the
     * fields from the constructor that have been used in every method
     *
     * @param adapter
     * @param target
     * @return true - if the adapter contains all necessary methods from target
     * false - if otherwise
     */
    private boolean getMethodFields(IClassModel adapter, IClassModel target) {
        Collection<? extends IMethodModel> targetMethods = target.getMethods();

        Set<IMethodModel> decoratedMethods = new HashSet<>();
        adapter.getMethods().stream()
                .filter((method) -> isDecoratedMethod(method, targetMethods))
                .forEach(decoratedMethods::add);

        if(decoratedMethods.size() != targetMethods.size()){
        	return false;
        }
        
        for (IMethodModel method : decoratedMethods) {
            Set<IFieldModel> commonFields = new HashSet<>();
            Collection<? extends IFieldModel> fieldsUsed = method.getAccessedFields();
            for (IFieldModel field : fieldsUsed) {
            	ITypeModel type = field.getFieldType();
                if (this.active.contains(field) && type.getDimension() == 0 && type.getClassModel() != null) {
                    commonFields.add(field);
                }
            }
            this.active = commonFields;
        }

        return true;
    }

    /**
     * Determines if adapter is a subclass of target and determines if some constructor in adapter
     * contains arguments
     *
     * @param adapter
     * @param target
     * @return true - if all criteria are met
     * false - if otherwise
     */
    private boolean getConstructed(IClassModel adapter, IClassModel target) {
        if (!adapter.getSuperClass().equals(target) && !adapter.getInterfaces().contains(target)) {
            return false;
        }

        List<? extends ITypeModel> arguments;
        Collection<? extends IMethodModel> methods = adapter.getMethods();
        Collection<? extends IFieldModel> fields = adapter.getFields();
        for (IMethodModel method : methods) {
            if (method.getMethodType() == MethodType.CONSTRUCTOR) {
                arguments = method.getArguments();
                for (ITypeModel type : arguments) {
                    for (IFieldModel field : fields) {
                        if (field.getFieldType().equals(type)) {
                            this.active.add(field);
                        }
                    }
                }
            }
        }return !this.active.isEmpty();
        
    }
}
