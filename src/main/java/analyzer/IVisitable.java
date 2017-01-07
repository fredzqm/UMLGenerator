package analyzer;

/**
 * Any class that implements this interface can be tranversed with corresponding
 * IVisitor
 * 
 * @author zhang
 *
 * @param <T>
 */
public interface IVisitable<T> {
	void visit(IVisitor<T> IVisitor);
}
