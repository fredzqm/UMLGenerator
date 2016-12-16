package generator;

/**
 * The IFormat for the format created in analyzer.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IFormat {
    /**
     * Returns the Node Separation value.
     *
     * @return Node Separation.
     */
    double getNodeSep();

    /**
     * Returns either BT or TB depending on how you want the
     * UML to show.
     *
     * @return BT or TB
     */
    String getRankDir();

    /**
     * Returns the Super Classes Edge Style.
     *
     * @return Super Class Edge Styling.
     */
    String superclassEdgeStyle();

    /**
     * Returns the Graph styling for every Node.
     *
     * @return Node styling for entire graph.
     */
    String getNodeStyle();

    /**
     * Returns the Inheritance Edge style.
     *
     * @return Inheritance Edge style.
     */
    String getInheritanceEdgeStyle();

    /**
     * Returns the Has-A Edge style.
     *
     * @return Has-A Edge style.
     */
    String getHasAEdgeStyle();

    /**
     * Returns the Depend-On Edge style.
     *
     * @return Depend-On Edge style.
     */
    String getDependOnEdgeStyle();
}
