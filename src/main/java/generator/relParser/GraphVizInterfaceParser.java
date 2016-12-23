package generator.relParser;

import generator.IClassModel;

/**
 * A GraphVizParser for the model's interface.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizInterfaceParser implements IParseGuide {

	@Override
	public String getEdgeStyle() {
		return "arrowhead=onormal style=dashed ";
	}

	@Override
	public Iterable<? extends IClassModel> getRelatesTo(IClassModel thisClass) {
		return thisClass.getInterfaces();
	}

}