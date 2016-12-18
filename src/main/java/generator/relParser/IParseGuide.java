package generator.relParser;

import generator.IClassModel;
import generator.classParser.IParser;

/**
 * An Interface for a ParseGuide.
 * <p>
 * Created by lamd on 12/16/2016.
 */
public interface IParseGuide extends IParser<IClassModel> {
    /**
     * Returns the edge style.
     *
     * @return String describing the edge of this ParseGuide.
     */
    String getEdgeStyle();

}
