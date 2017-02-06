package analyzer.relationParser;

import analyzer.utility.StyleMap;

/**
 * RelationInfo for bijective has-a relations.
 */
public class RelationHasABijective implements IRelationInfo {
    public static final String REL_KEY = "has_a_bijective";

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
    public StyleMap getStyleMap() {
        StyleMap styleMap = getForward().getStyleMap();
        styleMap.addStyle("dir", "both");

        RelationHasA backward = getForward();
        if (backward.isMany()) {
            styleMap.addStyle("taillabel", String.format("%d..*", backward.getCount()));
        } else {
            styleMap.addStyle("taillabel", String.format("%d", backward.getCount()));
        }

        return styleMap;
    }

    @Override
    public String getRelKey() {
        return RelationHasABijective.REL_KEY;
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
