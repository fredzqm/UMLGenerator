package generator.relParser;

import generator.classParser.IClassModel;

/**
 * A GraphVizParser for the model's HasRelations.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizHasRelParser implements IParseGuide {
	
	@Override
	public String getEdgeStyle() {
		return "arrowhead=vee style=\"\"";
	}

	@Override
	public Iterable<? extends IClassModel> getRelatesTo(IClassModel thisClass) {
		return thisClass.getHasRelation().keySet();
	}

}