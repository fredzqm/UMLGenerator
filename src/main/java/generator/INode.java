package generator;

public interface INode {

    /**
     * @return the name for this vertex
     */
    String getName();

    /**
     * @return the label for this vertex
     */
    String getLabel();
    
    /**
     * 
     * @return the style of this vertex
     */
    String getNodeStyle();
}
