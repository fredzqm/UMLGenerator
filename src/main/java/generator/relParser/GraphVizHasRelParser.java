package generator.relParser;

import generator.IClassModel;

/**
 * A GraphVizParser for the model's HasRelations.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizHasRelParser implements IParseGuide {
	
	@Override
	public String parse(IClassModel thisClass) {
		Iterable<? extends IClassModel> otherClassList = thisClass.getHasRelation().keySet();
		StringBuilder sb = new StringBuilder();
		otherClassList.forEach((has) -> {sb.append(String.format("\"%s\" ", has.getName()));});
		return String.format("\t\"%s\" -> {%s};\n", thisClass.getName(), sb.toString());
	}

	@Override
	public String getEdgeStyle() {
		return "arrowhead=vee style=\"\"";
	}

}