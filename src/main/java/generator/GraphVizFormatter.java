package generator;

/**
 * A IFormatter for the GraphVizGenerator.
 * <p>
 * Created by lamd on 12/16/2016.
 */
public class GraphVizFormatter<T> implements IFormatter {
    private IParser<T> modelParser;
    private String edgeStyle;

    /**
     * Constructs a GraphVizFormatter.
     *
     * @param modelParser IParser for a model
     * @param edgeStyle   String of the edgeStyle
     *                    Example: "edge [arrowhead=onormal]"
     */
    public GraphVizFormatter(IParser<T> modelParser, String edgeStyle) {
        this.modelParser = modelParser;
        this.edgeStyle = edgeStyle;
    }


    @Override
    public boolean hasEdgeStyle() {
        return (this.edgeStyle != null);
    }

    @Override
    public String getEdgeStyle() {
        return this.edgeStyle;
    }

    @Override
    public String parse(Iterable classes) {
        return this.modelParser.parse(classes);
    }
}
