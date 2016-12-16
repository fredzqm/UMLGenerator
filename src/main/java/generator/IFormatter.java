package generator;

/**
 * Created by lamd on 12/16/2016.
 */
public interface IFormatter {
    boolean hasEdgeStyle();

    String getEdgeStyle();

    String parse(Iterable<? extends IClassModel> classes);
}
