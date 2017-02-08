package analyzer.decorator;

import analyzer.utility.IClassModel;
import analyzer.utility.IMethodModel;
import config.IConfiguration;
import utility.MethodType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A Bad Decorator Analyzer. It will highlight classes that are likely decorators that are incomplete in yellow (default).
 * <p>
 * Created by lamd on 2/2/2017.
 */
public class BadDecoratorAnalyzer extends DecoratorTemplate {
    private boolean missingParentMethodDecoration(IClassModel child, IClassModel parent) {
        Collection<? extends IMethodModel> parentMethods = parent.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD).collect(Collectors.toList());

        Set<IMethodModel> decoratedMethods = new HashSet<>();
        System.out.println();
        System.out.println(child.getName());
        System.out.println(child.getMethods());
        System.out.println(parent.getName());
        System.out.println(parentMethods);

        System.out.println("MATCHING METHODS");
        child.getMethods().stream()
                .filter((method) -> method.getMethodType() == MethodType.METHOD && isDecoratedMethod(method, parentMethods))
                .forEach(decoratedMethods::add);

        System.out.println("SIZE:");
        System.out.println("\tdecoration size: " + decoratedMethods.size());
        System.out.println("\tparent size: " + parentMethods.size());
        return decoratedMethods.size() != parentMethods.size();
    }

    @Override
    protected IAdapterDecoratorConfiguration setupConfig(IConfiguration config) {
        return config.createConfiguration(BadDecoratorConfiguration.class);
    }

    @Override
    protected boolean detectPattern(IClassModel child, IClassModel parent) {
        return hasParentAsField(child, parent) && hasParentAsConstructorArgument(child, parent)
                && missingParentMethodDecoration(child, parent);
    }
}
