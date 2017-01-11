package analyzerRelationParser;

import analyzer.IRelationInfo;

/**
 * RelationInfo that interprets depends-on relation.
 */
public class RelationDependsOn implements IRelationInfo {
    private final boolean many;
    private final int count;

    /**
     * Constructs a RelationHasA object.
     *
     * @param count count value of the relation.
     */
    RelationDependsOn(int count) {
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
            return String.format("Depends on %d..n", this.count);
        } else {
            return "has " + getCount();
        }
    }

    @Override
    public String getEdgeStyle() {
        StringBuilder edgeBuilder = new StringBuilder("arrowhead=\"vee\" style=dashed ");

        if (isMany() || getCount() > 1) {
            edgeBuilder.append("taillabel=\"1..*\" ");
        }

        return edgeBuilder.toString();
    }

}
