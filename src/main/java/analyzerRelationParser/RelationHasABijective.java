package analyzerRelationParser;

import analyzer.IRelationInfo;

/**
 * RelationInfo for bijective has-a relations.
 */
public class RelationHasABijective implements IRelationInfo {
    private RelationHasA a, b;

    /**
     * Constructs a RelationHasABijective object.
     *
     * @param aRel RelationHasA to relation.
     * @param bRel RelationHasA from relation.
     */
    RelationHasABijective(RelationHasA aRel, RelationHasA bRel) {
        this.a = aRel;
        this.b = bRel;
    }

    RelationHasA getForward() {
        return a;
    }

    RelationHasA getBackward() {
        return b;
    }

    @Override
    public String toString() {
        return "" + a + " & " + b;
    }

    @Override
    public String getEdgeStyle() {
        RelationHasA forward = getForward();
        StringBuilder edgeBuilder = new StringBuilder("arrowhead=\"vee\" style=\"\" dir=both ");

        if (forward.isMany() || forward.getCount() > 1) {
            edgeBuilder.append("headlabel=\"1..*\" ");
        }

        RelationHasA backward = getBackward();
        if (backward.isMany() || backward.getCount() > 1) {
            edgeBuilder.append("taillabel=\"1..*\" ");
        }

        return edgeBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == RelationHasABijective.class) {
            RelationHasABijective x = (RelationHasABijective) obj;
            return x.a.equals(this.a) && x.b.equals(this.b);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.a.hashCode() * 28 + this.b.hashCode() * 15;
    }

}
