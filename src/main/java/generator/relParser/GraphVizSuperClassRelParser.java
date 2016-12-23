package generator.relParser;

import java.util.ArrayList;

import generator.classParser.IClassModel;

/**
 * A GraphVizParser for the model's SuperClass.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizSuperClassRelParser implements IParseGuide {

	@Override
	public String getEdgeStyle() {
		return "arrowhead=onormal style=\"\"";
	}

	@Override
	public Iterable<? extends IClassModel> getRelatesTo(IClassModel thisClass) {
		ArrayList<IClassModel> ls = new ArrayList<>();
		if (thisClass.getSuperClass() != null)
			ls.add(thisClass.getSuperClass());
		return ls;
	}

}