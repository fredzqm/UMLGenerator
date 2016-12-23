package generator.relParser;

/**
 * A GraphVizParser for the model's depends on Relationship.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizDependsOnRelParser implements IParseGuide {
	
    @Override
    public String getEdgeStyle(Relation edge) {
        return "arrowhead=vee style=dashed ";
    }

}