package analyzer;

/**
 * Any class that implements this interface can be tranversed with corresponding
 * IVisitor
 *
 * @param <T>
 * @author zhang
 */
public interface IVisitable<T> {
    /**
     * TODO: FRED Document this.
     *
     * @param IVisitor
     */
    void visit(IVisitor<T> IVisitor);
}
