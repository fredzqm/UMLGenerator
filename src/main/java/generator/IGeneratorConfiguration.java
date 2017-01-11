package generator;

/**
 * An Interface for Generator Configuration.
 * <p>
 * Created by lamd on 12/12/2016. Edited by fineral on 12/13/2016.
 */
public interface IGeneratorConfiguration {
    /**
     * Returns the Node Seperation.
     *
     * @return Node Separation value.
     */
    double getNodeSep();

    /**
     * Returns either BT or TB depending on how you want the UML to show.
     *
     * @return BT or TB
     */
    String getRankDir();

    /**
     * Returns the Graph styling for every Node.
     *
     * @return Node styling for entire graph.
     */
    String getNodeStyle();
}
