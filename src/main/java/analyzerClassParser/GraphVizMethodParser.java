package analyzerClassParser;

import analyzer.IMethodModel;

/**
 * A GraphVizParser for the model's Methods.
 * <p>
 * Created by lamd on 12/14/2016.
 */
class GraphVizMethodParser implements IParser<IMethodModel> {
    @Override
    public String parse(IMethodModel method) {
        StringBuilder classMethodBuilder = new StringBuilder();

        // Add the modifier.
        String modifierSymbol = method.getModifier().getModifierSymbol();
        // We need to escape the space for default methods.
        if (modifierSymbol.equals(" ")) {
            classMethodBuilder.append(" \\").append(modifierSymbol);
        } else {
            classMethodBuilder.append(modifierSymbol);
        }
        classMethodBuilder.append(" ");

        // Add the name.
        classMethodBuilder.append(method.getName());
        classMethodBuilder.append("(");

        // Add the arguments.
        int methodLengthBefore = classMethodBuilder.length();
        method.getArgumentTypeNames().forEach((type) -> {
            // Java does not keep track of variable names.
            classMethodBuilder.append(String.format("%s, ", type));
        });

        // Remove the ", " and end method with parenthesis.
        if (methodLengthBefore != classMethodBuilder.length()) {
            classMethodBuilder.replace(classMethodBuilder.length() - 2, classMethodBuilder.length(), ")");
        } else {
            classMethodBuilder.append(")");
        }

        // Add the return type.
        classMethodBuilder.append(String.format(" : %s \\l", method.getReturnTypeName()));

        return classMethodBuilder.toString();
    }
}