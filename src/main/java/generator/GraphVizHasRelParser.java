package generator;

import java.util.Collection;

/**
 * A GraphVizParser for the model's HasRelations.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizHasRelParser implements IParser<IClassModel> {
	private Collection<IModifier> filters;

	public GraphVizHasRelParser(Collection<IModifier> filters) {
		this.filters = filters;
	}

	@Override
	public String parse(IClassModel thisClass) {
		Iterable<? extends IClassModel> otherClassLs = thisClass.getHasRelation();

		StringBuilder sb = new StringBuilder();
		GraphVizDependencyFormatter.setupDependencyVizDescription(sb, thisClass.getName());
		int hasALengthBefore = sb.length();
		otherClassLs.forEach((has) -> {
			if (!filters.contains(has.getModifier())) {
				sb.append("\"").append(has.getName()).append("\"");
				sb.append(", ");
			}
		});
		GraphVizDependencyFormatter.closeDependencyVizDescription(sb, hasALengthBefore);

		sb.append("\n\t");
		return sb.toString();
	}

}
