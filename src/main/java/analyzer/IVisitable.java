package analyzer;

/**
 * An Interface for a Visitable object.
 *
 * @param <T>
 */
public interface IVisitable<T> {
    void visit(IVisitor<T> IVisitor);
}
