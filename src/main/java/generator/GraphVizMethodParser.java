package generator;

import java.util.Collection;

/**
 * A GraphVizParser for the model's Methods.
 *
 * Created by lamd on 12/14/2016.
 */
public class GraphVizMethodParser implements IGraphVizParser {
    private StringBuilder classMethod;

    public GraphVizMethodParser(Iterable<? extends IMethodModel> methods, Collection<IModifier> filters) {
        this.classMethod = new StringBuilder();
        generateMethods(methods, filters);
    }

    @Override
    public String getOutput() {
        return this.classMethod.toString();
    }

    private void generateMethods(Iterable<? extends IMethodModel> methods, Collection<IModifier> filters) {
        methods.forEach((method) -> {
            if (!filters.contains(method.getModifier())) {
                // Add the modifier.
                this.classMethod.append(method.getModifier().getModifierSymbol());
                this.classMethod.append(" ");

                // Add the name.
                this.classMethod.append(method.getName());
                this.classMethod.append("(");

                // Add the arguments.
                int methodLengthBefore = this.classMethod.length();
                method.getArguments().forEach((type) -> {
                    this.classMethod.append(type.getName());
                    // Java does not keep track of variable names.
//                this.classMethod.append(" : ");
//                this.classMethod.append(type.getName());
                    this.classMethod.append(", ");
                });

                // Remove the ", " and end method with parenthesis.
                if (methodLengthBefore != this.classMethod.length()) {
                    this.classMethod.replace(this.classMethod.length() - 2, this.classMethod.length(), ")");
                } else {
                    this.classMethod.append(")");
                }

                // Add the return type.
                this.classMethod.append(" : ");
                this.classMethod.append(method.getReturnType().getName());
                this.classMethod.append("\\l ");
            }
        });
    }
}
