package analyzer;

import generator.IEdge;

public class Relation implements IEdge {
	private ClassPair classPair;
	private IRelationInfo info;

	public Relation() {
	}

	public void set(ClassPair classPair, IRelationInfo info) {
		this.classPair = classPair;
		this.info = info;
	}

	public ClassPair getClassPair() {
		return classPair;
	}

	/**
	 * Returns the name of the class that it points from.
	 *
	 * @return String of the Class name this relationship is pointing from.
	 */
	public String getFrom() {
		return this.classPair.getFrom().getName();
	}

	/**
	 * Returns the name of the class it is pointing to.
	 *
	 * @return String of the Class name this relationship is pointing to.
	 */
	public String getTo() {
		return this.classPair.getTo().getName();
	}

	/**
	 * Returns the Relationship's Info.
	 *
	 * @return Relationship's Info.
	 */
	public IRelationInfo getInfo() {
		return this.info;
	}

	@Override
	public String toString() {
		return "" + classPair.getFrom() + " -> " + classPair.getTo() + ":\t\t" + info + "\n";
	}

	public String getEdgeStyle() {
		return info.getEdgeStyle();
	}
}
