package models;

public interface Visitor<T> {
	void visit(T node);
}
