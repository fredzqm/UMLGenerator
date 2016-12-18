package generator;

/**
 * A IParseGuide for the GraphVizGenerator.
 * <p>
 * Created by lamd on 12/16/2016.
 */
public class GraphVizParseGuide implements IParseGuide {
    private final IParser<IClassModel> modelParser;
    private final String edgeStyle;

    /**
     * Constructs a GraphVizParseGuide.
     *
     * @param modelParser IParser for a model
     * @param edgeStyle   String of the edgeStyle
     *                    Example: "edge [arrowhead=onormal]"
     */
    GraphVizParseGuide(IParser<IClassModel> modelParser, String edgeStyle) {
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
    public String parse(Iterable<? extends IClassModel> classes) {
        return this.modelParser.parse(classes);
    }
}
