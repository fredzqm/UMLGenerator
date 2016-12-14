package generator;

import java.util.Collection;

/**
 * A GraphVizParser for the model's SuperClass.
 *
 * Created by lamd on 12/14/2016.
 */
public class GraphizExtendsRelParser implements IRelationParser {
	private Collection<IModifier> filters;

	public GraphizExtendsRelParser(Collection<IModifier> filters) {
		this.filters = filters;
	}

	@Override
	public String parse(IClassModel thisClass, IClassModel superClass) {
		// Ensure the superclass is not null and the filter does not exclude it.
		// Uses shortcircuiting.
		if (superClass == null || filters.contains(superClass.getModifier())) {
			return "";
		}
		StringBuilder sb = new StringBuilder();

		GraphVizDependencyFormatter.setupDependencyVizDescription(sb, thisClass.getName());

		sb.append("\"").append(superClass.getName()).append("\"");
		sb.append("};\n");
		return sb.toString();
	}
}
