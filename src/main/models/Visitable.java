package models;

public interface Visitable<T> {
	void visit(Visitor<T> visitor);
}
