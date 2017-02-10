package analyzer.adapter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import analyzer.decorator.AdapterDecoratorTemplate;
import analyzer.decorator.IAdapterDecoratorConfiguration;
import analyzer.relationParser.RelationHasA;
import analyzer.utility.IClassModel;
import analyzer.utility.IMethodModel;
import analyzer.utility.ISystemModel;
import config.IConfiguration;

/**
 * An Adapter Pattern Analyzer. It will highlight in some color (defaulted to
 * red) all classes part of the Adapter pattern instance.
 * <p>
 * Created by fineral on 2/9/2017.
 */
public class AdapterDetectorAnalyzer extends AdapterDecoratorTemplate {

    @Override
    protected IAdapterDecoratorConfiguration setupConfig(IConfiguration config) {
        return config.createConfiguration(AdapterDetectorConfiguration.class);
    }

    @Override
    protected boolean detectPattern(IClassModel clazz, IClassModel composedClazz, IClassModel parent,
            Set<IMethodModel> overridingMethods) {
        if (clazz.isSubClazzOf(composedClazz))
            return false;
        if (usedByAllAdaptedMethods(composedClazz, overridingMethods)) {
            return true;
        }
        return false;
    }

    private boolean usedByAllAdaptedMethods(IClassModel type, Set<IMethodModel> adaptedMethods) {
        Set<IMethodModel> methodCalledOnTheField = new HashSet<>();
        for (IMethodModel method : adaptedMethods) {
            Collection<? extends IMethodModel> methodAccessed = method.getCalledMethods().stream()
                    .filter((m) -> m.getBelongTo().equals(type)).collect(Collectors.toList());
            methodCalledOnTheField.addAll(methodAccessed);
        }
        return methodCalledOnTheField.size() == adaptedMethods.size();
    }

    @Override
    protected void styleChild(ISystemModel systemModel, IClassModel child) {
        // TODO Auto-generated method stub
        super.styleChild(systemModel, child);
    }
    
    @Override
    protected void styleComposedClass(ISystemModel systemModel, IClassModel composedClass) {
        addCommonFillColor(systemModel, composedClass);
        systemModel.addClassModelSteretypes(composedClass, this.config.getRelatedClassStereotype());
    }
    
    @Override
    protected void styleComposedClassRelationship(ISystemModel systemModel, IClassModel clazz, IClassModel composedClazz) {
        systemModel.addStyleToRelation(clazz, composedClazz, RelationHasA.REL_KEY, "xlabel",
                this.config.getChildParentRelationshipLabel());
    }
    
}
