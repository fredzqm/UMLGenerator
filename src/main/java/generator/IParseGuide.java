package generator;

/**
 * An Interface for a ParseGuide.
 * <p>
 * Created by lamd on 12/16/2016.
 */
public interface IParseGuide {
    /**
     * Returns true if there is an edge style defined.
     *
     * @return true if edge style defined.
     */
    boolean hasEdgeStyle();

    /**
     * Returns the edge style.
     *
     * @return String describing the edge of this ParseGuide.
     */
    String getEdgeStyle();

    /**
     * Returns a string with classes parsed according to the given parser.
     *
     * @param classes Iterable of class models to parse.
     * @return Parsed String.
     */
    String parse(Iterable<? extends IClassModel> classes);
}
