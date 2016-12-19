package generator.relParser;

import generator.IClassModel;

/**
 * A GraphVizParser for the model's interface.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizInterfaceParser implements IParseGuide {

	@Override
	public String parse(IClassModel thisClass) {
		Iterable<? extends IClassModel> otherClassList = thisClass.getInterfaces();
		StringBuilder sb = new StringBuilder();
		otherClassList.forEach((interfaceModel) -> sb.append(String.format("\"%s\" ", interfaceModel.getName())));
		return String.format("\t\"%s\" -> {%s};\n", thisClass.getName(), sb.toString());
	}

	@Override
	public String getEdgeStyle() {
		return "arrowhead=onormal style=dashed ";
	}

}