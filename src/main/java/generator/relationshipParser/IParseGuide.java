package generator.relationshipParser;

/**
 * An Interface for a GraphVizParseGuide.
 * <p>
 * Created by lamd on 12/16/2016.
 */
public interface IParseGuide {

    /**
     * Returns the edge style.
     *
     * @param info each edge may have totally different data structure to
     *             represent specifications of this relation.
     * @return String describing the edge of this GraphVizParseGuide.
     */
    String getEdgeStyle(IRelationInfo info);

}
