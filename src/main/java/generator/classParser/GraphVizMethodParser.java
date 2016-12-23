package generator.classParser;

/**
 * A GraphVizParser for the model's Methods.
 * <p>
 * Created by lamd on 12/14/2016.
 */
class GraphVizMethodParser implements IParser<IMethodModel> {

	@Override
	public String parse(IMethodModel method) {
		StringBuilder classMethod = new StringBuilder();
		// Add the modifier.
		String modifierSymbol = method.getModifier().getModifierSymbol();
		// We need to escape the space for default methods.
		if (modifierSymbol.equals(" ")) {
			classMethod.append(" \\").append(modifierSymbol);
		} else {
			classMethod.append(modifierSymbol);
		}
		classMethod.append(" ");

		// Add the name.
		classMethod.append(method.getName());
		classMethod.append("(");

		// Add the arguments.
		int methodLengthBefore = classMethod.length();
		method.getArguments().forEach((type) -> {
			// Java does not keep track of variable names.
			classMethod.append(String.format("%s, ", type.getName()));
		});

		// Remove the ", " and end method with parenthesis.
		if (methodLengthBefore != classMethod.length()) {
			classMethod.replace(classMethod.length() - 2, classMethod.length(), ")");
		} else {
			classMethod.append(")");
		}

		// Add the return type.
		classMethod.append(String.format(" : %s \\l", method.getReturnType().getName()));

		return classMethod.toString();
	}
}