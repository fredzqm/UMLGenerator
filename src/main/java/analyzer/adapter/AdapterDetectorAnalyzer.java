package analyzer.adapter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import analyzer.decorator.AdapterDecoratorTemplate;
import analyzer.decorator.IAdapterDecoratorConfiguration;
import analyzer.relationParser.RelationHasA;
import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;
import analyzer.utility.ISystemModel;
import analyzer.utility.ITypeModel;
import config.IConfiguration;
import model.Signature;
import utility.MethodType;

/**
 * A Good Decorator Pattern Analyzer. It will highlight in green all suspected decorator classes in green (default).
 * <p>
 * Created by lamd on 2/2/2017.
 */
public class AdapterDetectorAnalyzer extends AdapterDecoratorTemplate {
	
	private Set<IFieldModel> active = new HashSet<>(); 
    
	private void addCommonDecoratorStyle(ISystemModel systemModel, IClassModel classModel) {
        systemModel.addClassModelStyle(classModel, "style", "filled");
        systemModel.addClassModelStyle(classModel, "fillcolor", this.config.getFillColor());
    }
	
	@Override
	protected void styleParent(ISystemModel systemModel, IClassModel parent) {
		addCommonDecoratorStyle(systemModel, parent);
        systemModel.addClassModelSteretypes(parent, this.config.getParentStereotype());
		
	}

	@Override
	protected void styleChild(ISystemModel systemModel, IClassModel child) {
		addCommonDecoratorStyle(systemModel, child);
        systemModel.addClassModelSteretypes(child, this.config.getChildStereotype());
	}

	@Override
	protected void styleChildParentRelationship(ISystemModel systemModel, IClassModel child, IClassModel parent) {
		systemModel.addStyleToRelation(child, parent, RelationHasA.REL_KEY, "xlabel",
                this.config.getChildParentRelationshipLabel());
	}
	
	@Override
    protected void updateRelatedClasses(ISystemModel systemModel, IClassModel clazz) {
		IClassModel adaptee = active.iterator().next().getFieldType().getClassModel();
		addCommonDecoratorStyle(systemModel, adaptee);
        systemModel.addClassModelSteretypes(adaptee, ((AdapterDetectorConfiguration) this.config).getAdapteeStereotype());
    }

	@Override
	protected IAdapterDecoratorConfiguration setupConfig(IConfiguration config) {
		return config.createConfiguration(AdapterDetectorConfiguration.class);
	}

	@Override
	protected boolean detectPattern(IClassModel adapter, IClassModel target) {
		return getConstructed(adapter, target) && getMethodFields(adapter, target) && singleField(adapter,target);
	}
	
	private boolean singleField(IClassModel adapter, IClassModel target) {
		return active.size() == 1;
	}
	
	private boolean getMethodFields(IClassModel adapter, IClassModel target){
		Collection<? extends IMethodModel> targetMethods = target.getMethods();

        Set<IMethodModel> decoratedMethods = new HashSet<>();
        adapter.getMethods().stream()
                .filter((method) -> isDecoratedMethod(method, targetMethods))
                .forEach(decoratedMethods::add);
        
        
        for(IMethodModel method: decoratedMethods){
        	Set<IFieldModel> commonFields = new HashSet<IFieldModel>();
        	Collection<? extends IFieldModel> fieldsUsed = method.getAccessedFields();
        	for(IFieldModel field: fieldsUsed){
        		if(active.contains(field)){
        			commonFields.add(field);
        		}
        	}
        	active = commonFields;
        }
        
        return decoratedMethods.size() == targetMethods.size();
	}
	
	private boolean getConstructed(IClassModel adapter, IClassModel target) {
		if(!adapter.getSuperClass().equals(target) && !adapter.getInterfaces().contains(target)){
			return false;
		}
		List<? extends ITypeModel> arguments;
        Collection<? extends IMethodModel> methods = adapter.getMethods();
        Collection<? extends IFieldModel> fields = adapter.getFields();
        for (IMethodModel method : methods) {
            if (method.getMethodType() == MethodType.CONSTRUCTOR) {
                arguments = method.getArguments();
                for (ITypeModel type : arguments) {
                	for (IFieldModel field: fields){
	                    if (field.getFieldType().equals(type)) {
	                    	active.add(field);
	                    }
                	}
                }
            }
        }
        if(active.isEmpty()){
        	return false;
        }
        return true;
	}
	
//	private boolean hasAdapteeAsField(IClassModel adapter, IClassModel adaptee){
//		Collection<? extends IFieldModel> fields = adapter.getFields();
//        for (IFieldModel field : fields) {
//            if (field.getFieldType().equals(adaptee)) {
//                return true;
//            }
//        }
//        return false;
//	}
	
	protected boolean isDecoratedMethod(IMethodModel method, Collection<? extends IMethodModel> targetMethods) {
        Signature methodSignature = method.getSignature();
        for (IMethodModel parentMethod : targetMethods) {
            Signature signature = parentMethod.getSignature();
            if (signature.equals(methodSignature)) {
                return true;
            }
        }
        return false;
    }
	
//	protected boolean isAdaptedFieldCalled(IClassModel adaptee, IMethodModel method) {
//        for (IFieldModel field : method.getAccessedFields()) {
//            if (field.getFieldType().equals(adaptee)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
