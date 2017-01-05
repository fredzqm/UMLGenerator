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

        // Debug print.
        System.out.println("From Cardinality: " + edge.getCardinalityFrom());

        if (edge.getCardinalityTo() > 0) {
            edgeBuilder.append("headlabel=0..* ");
        }
        if (edge.getCardinalityFrom() > 0) {
            edgeBuilder.append("taillabel=0..* ");
        }

        return edgeBuilder.toString();
    }
}