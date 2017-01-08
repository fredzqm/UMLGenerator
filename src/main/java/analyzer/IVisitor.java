package analyzer;

/**
 * A visitor interface that allows analyzer to perform recursive search
 * 
 * @author zhang
 *
 * @param <T>
 */
public interface IVisitor<T> {
	void visit(T node);
}
