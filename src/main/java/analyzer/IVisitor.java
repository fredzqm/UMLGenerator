package analyzer;

/**
 * A visitor interface that allows analyzer to perform recursive search
 *
 * @param <T>
 * @author zhang
 */
public interface IVisitor<T> {
    /**
     * TODO: Fred
     *
     * @param node
     */
    void visit(T node);
}
