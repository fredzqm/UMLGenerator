package generator.relParser;

/**
 * A GraphVizParser for the model's depends on Relationship.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizDependsOnRelParser implements IParseGuide {

    @Override
    public String getEdgeStyle(IRelation edge) {
        StringBuilder edgeBuilder = new StringBuilder("arrowhead=vee style=dashed ");
        if (edge.isBijective()) {
            edgeBuilder.append("arrowtail=vee dir=both ");
        }

        return edgeBuilder.toString();
    }

}