package generator.relParser;

import generator.IClassModel;

/**
 * An Interface for a ParseGuide.
 * <p>
 * Created by lamd on 12/16/2016.
 */
public interface IParseGuide {
    /**
     * Returns the edge style.
     *
     * @return String describing the edge of this ParseGuide.
     */
    String getEdgeStyle();

    Iterable<? extends IClassModel> getRelatesTo(IClassModel thisClass);
}
