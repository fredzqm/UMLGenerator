package analyzerRelationParser;

import analyzer.IRelationInfo;

/**
 * RelationInfo that interprets depends-on relation.
 */
public class RelationDependsOn implements IRelationInfo {
    private final boolean many;

    /**
     * Constructs a RelationHasA object.
     *
     * @param count count value of the relation.
     */
    public RelationDependsOn(boolean many) {
        this.many = many;
    }

    /**
     * Returns true if this is a one-to-many relationship.
     *
     * @return true if it is a one-to-many relationship.
     */
    boolean isMany() {
        return this.many;
    }

    @Override
    public String toString() {
        if (isMany()) {
            return "Depends on many";
        } else {
            return "Depends on ";
        }
    }

    @Override
    public String getEdgeStyle() {
        StringBuilder edgeBuilder = new StringBuilder("arrowhead=\"vee\" style=\"dashed\" ");

        if (isMany()) {
            edgeBuilder.append("taillabel=\"0..*\" ");
        }

        return edgeBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == RelationDependsOn.class) {
            RelationDependsOn x = (RelationDependsOn) obj;
            return x.many == this.many;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (many ? 1 : 15);
    }

}
