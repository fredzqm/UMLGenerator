package analyzer;

public interface IVisitor<T> {
	void visit(T node);
}
