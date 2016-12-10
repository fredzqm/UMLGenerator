package models;

public interface IVisitor<T> {
	void visit(T node);
}
