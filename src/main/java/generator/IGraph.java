package generator;

/**
 * An interface for the Graph used by Generator.
 */
public interface IGraph {
    /**
     * Returns an Iterable of Class Models contained within the System Model.
     *
     * @return Iterable of Class Models.
     */
    Iterable<? extends INode> getVertices();

    /**
     * Returns an Iterable of Relations contained within the SystemModel.
     *
     * @return Iterable of Relations contained within the SystemModel.
     */
    Iterable<? extends IEdge> getEdges();
}
