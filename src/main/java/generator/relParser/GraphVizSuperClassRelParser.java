package generator.relParser;

import generator.IClassModel;

/**
 * A GraphVizParser for the model's SuperClass.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizSuperClassRelParser implements IParseGuide {

	@Override
	public String parse(IClassModel thisClass) {
		IClassModel superClass = thisClass.getSuperClass();
		if (superClass == null) {
			return "";
		}
		return String.format("\t\"%s\" -> {\"%s\" };\n", thisClass.getName(), superClass.getName());
	}

	@Override
	public String getEdgeStyle() {
		return "edge [arrowhead=onormal]";
	}

}