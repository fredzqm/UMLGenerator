package generator.relParser;

import generator.IClassModel;
import utility.IFilter;
import utility.Modifier;

/**
 * A GraphVizParser for the model's depends on Relationship.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizDependsOnRelParser implements IParseGuide {
	private IFilter<Modifier> modifierFilter;

	public GraphVizDependsOnRelParser(IFilter<Modifier> filter) {
		this.modifierFilter = filter;
	}

	@Override
	public String parse(IClassModel thisClass) {
		Iterable<? extends IClassModel> otherClassList = thisClass.getDependsRelation();

		StringBuilder sb = new StringBuilder();
		GraphVizDependencyFormatter.setupDependencyVizDescription(sb, thisClass.getName());
		int dependsOnLengthBefore = sb.length();

		otherClassList.forEach((has) -> {
			sb.append(String.format("\"%s\" ", has.getName()));
			if (modifierFilter.filter(has.getModifier())) {
				sb.append(String.format("\"%s\" ", has.getName()));
			}
		});

		GraphVizDependencyFormatter.closeDependencyVizDescription(sb, dependsOnLengthBefore);
		return sb.toString();
	}

	@Override
	public String getEdgeStyle() {
		return "edge [arrowhead=vee style=dashed]";
	}

}