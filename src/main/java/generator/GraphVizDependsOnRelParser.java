package generator;

import java.util.Collection;

/**
 * A GraphVizParser for the model's depends on Relationship.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizDependsOnRelParser implements IParser<IClassModel> {
	private Collection<IModifier> filters;

	public GraphVizDependsOnRelParser(Collection<IModifier> filters) {
		this.filters = filters;
	}

	@Override
	public String parse(IClassModel thisClass) {
		Iterable<? extends IClassModel> otherClassLs = thisClass.getDependsRelation();

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
		return sb.toString();
	}

}
