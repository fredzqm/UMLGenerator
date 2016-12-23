package generator.relParser;

/**
 * A GraphVizParser for the model's HasRelations.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizHasRelParser implements IParseGuide {
    @Override
    public String getEdgeStyle(Relation edge) {
        return "arrowhead=vee style=\"\" ";
    }

}