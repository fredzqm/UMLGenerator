package generator.relParser;

import generator.IClassModel;

/**
 * A GraphVizParser for the model's depends on Relationship.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizDependsOnRelParser implements IParseGuide {
	
	@Override
	public String getEdgeStyle() {
		return "arrowhead=vee style=dashed ";
	}

	@Override
	public Iterable<? extends IClassModel> getRelatesTo(IClassModel thisClass) {
		return thisClass.getDependsRelation();
	}

}