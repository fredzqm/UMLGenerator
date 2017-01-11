package analyzerRelationParser;

import analyzer.IRelationInfo;

/**
 * RelationInfo that interprets Has-A Relationships.
 */
public class RelationHasA implements IRelationInfo {
    private final boolean many;
    private final int count;

    /**
     * Constructs a RelationHasA object.
     *
     * @param count
     *            count value of the relation.
     */
    RelationHasA(int count) {
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
            return String.format("has many %d..n", this.count);
        } else {
            return "has " + getCount();
        }
    }

    @Override
    public String getEdgeStyle() {
        StringBuilder edgeBuilder = new StringBuilder("arrowhead=\"vee\" style=\"\" ");
        if (isMany()) {
            edgeBuilder.append(String.format("taillabel=\"%d..*\" ", getCount()));
        } else {
            edgeBuilder.append(String.format("taillabel=\"%d\" ", getCount()));
        }
        return edgeBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == RelationHasA.class) {
            RelationHasA x = (RelationHasA) obj;
            return x.count == this.count && x.many == this.many;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return count * 31 + (many ? 1 : 15);
    }
}
