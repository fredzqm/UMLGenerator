package analyzer;

public interface IVisitable<T> {
	void visit(IVisitor<T> IVisitor);
}
