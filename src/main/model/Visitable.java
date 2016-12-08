package main.model;

public interface Visitable<T> {
	void visit(Visitor<T> visitor);
}
