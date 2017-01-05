package generator.relationshipParser;

public class RelationHasA implements IRelationInfo {
	private final boolean many;
	private final int count;

	public RelationHasA(int count) {
		this.many = count <= 0;
		this.count = Math.abs(count);
	}

	/**
	 * Returns true if this is a one-to-many relationship.
	 *
	 * @return true if it is a one-to-many relationship.
	 */
	boolean isMany() {
		return this.many;
	}

	/**
	 * Returns the exact cardinality of this relationship.
	 *
	 * @return Integer of the cardinality.
	 */
	public int getCount() {
		return this.count;
	}

	@Override
	public String toString() {
		if (isMany()) {
			return "has many " + getCount() + "..n";
		} else {
			return "has " + getCount();
		}
	}
}
