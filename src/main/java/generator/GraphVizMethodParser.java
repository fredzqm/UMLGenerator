package generator;

import java.util.Collection;

/**
 * A GraphVizParser for the model's Methods.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizMethodParser implements IParser<IMethodModel> {
    private Collection<IModifier> filters;

    public GraphVizMethodParser(Collection<IModifier> filters) {
        this.filters = filters;
    }

    @Override
    public String parse(IMethodModel method) {
        StringBuilder classMethod = new StringBuilder();
        if (!filters.contains(method.getModifier())) {
            // Add the modifier.
            classMethod.append(method.getModifier().getModifierSymbol());
            classMethod.append(" ");

            // Add the name.
            classMethod.append(method.getName());
            classMethod.append("(");

            // Add the arguments.
            int methodLengthBefore = classMethod.length();
            method.getArguments().forEach((type) -> {
                classMethod.append(type.getName());
                // Java does not keep track of variable names.
                // classMethod.append(" : ");
                // classMethod.append(type.getName());
                classMethod.append(", ");
            });

            // Remove the ", " and end method with parenthesis.
            if (methodLengthBefore != classMethod.length()) {
                classMethod.replace(classMethod.length() - 2, classMethod.length(), ")");
            } else {
                classMethod.append(")");
            }

            // Add the return type.
            classMethod.append(" : ");
            classMethod.append(method.getReturnType().getName());
            classMethod.append("\\l ");
        }
        return classMethod.toString();
    }

}
