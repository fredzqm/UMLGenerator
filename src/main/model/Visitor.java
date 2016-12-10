package main.model;

public interface Visitor<T> {
	void visit(T node);
}
