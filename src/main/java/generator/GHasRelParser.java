package generator;

import java.util.Collection;

/**
 * A GraphVizParser for the model's HasRelations.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GHasRelParser implements IParser<IClassModel> {
	private Collection<IModifier> filters;

	public GHasRelParser(Collection<IModifier> filters) {
		this.filters = filters;
	}

	@Override
	public String parse(IClassModel thisClass) {
		Iterable<? extends IClassModel> otherClassLs = thisClass.getHasRelation();

		StringBuilder sb = new StringBuilder();
		GDependencyFormatter.setupDependencyVizDescription(sb, thisClass.getName());
		int hasALengthBefore = sb.length();
		otherClassLs.forEach((has) -> {
			if (!filters.contains(has.getModifier())) {
				sb.append("\"").append(has.getName()).append("\"");
				sb.append(", ");
			}
		});
		GDependencyFormatter.closeDependencyVizDescription(sb, hasALengthBefore);

		sb.append("\n\t");
		return sb.toString();
	}

}
