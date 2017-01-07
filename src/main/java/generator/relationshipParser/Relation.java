package generator.relationshipParser;

public final class Relation {
	private final ClassPair classPair;
	private final IRelationInfo info;

	public Relation(ClassPair classPair, IRelationInfo info) {
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
		return this.classPair.getFrom();
	}

	/**
	 * Returns the name of the class it is pointing to.
	 *
	 * @return String of the Class name this relationship is pointing to.
	 */
	public String getTo() {
		return this.classPair.getTo();
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
