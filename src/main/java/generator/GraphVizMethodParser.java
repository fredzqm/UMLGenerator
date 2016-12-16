package generator;

import java.util.Collection;

import utility.IFilter;
import utility.Modifier;

/**
 * A GraphVizParser for the model's Methods.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizMethodParser implements IParser<IMethodModel> {
	private IFilter<Modifier> modifierFiter;

	GraphVizMethodParser(IFilter<Modifier> filters2) {
		this.modifierFiter = filters2;
	}

	@Override
	public String parse(IMethodModel method) {
		StringBuilder classMethod = new StringBuilder();
		if (modifierFiter.filter(method.getModifier())) {
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
				// classMethod.append(" : ");
				// classMethod.append(type.getName());
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
		}

		return classMethod.toString();
	}
}