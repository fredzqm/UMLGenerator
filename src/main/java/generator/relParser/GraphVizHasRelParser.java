package generator.relParser;

/**
 * A GraphVizParser for the model's HasRelations.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizHasRelParser implements IParseGuide {

    @Override
    public String getEdgeStyle(IRelation edge) {
        StringBuilder edgeBuilder = new StringBuilder("arrowhead=vee style=\"\" ");
        if (edge.isBijective()) {
            edgeBuilder.append("arrowtail=vee dir=both ");
        }

        return edgeBuilder.toString();
    }

}