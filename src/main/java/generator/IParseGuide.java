package generator;

/**
 * An Interface for a ParseGuide.
 *
 * Created by lamd on 12/16/2016.
 */
public interface IParseGuide<T> {
    boolean hasEdgeStyle();

    String getEdgeStyle();

    String parse(Iterable<? extends T> classes);
}
