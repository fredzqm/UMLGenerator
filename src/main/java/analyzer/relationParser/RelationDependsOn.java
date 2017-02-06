package analyzer.relationParser;

import analyzer.utility.StyleMap;

/**
 * RelationInfo that interprets depends-on relation.
 */
public class RelationDependsOn implements IRelationInfo {
    public static final String REL_KEY = "depends_on";

    private final boolean many;

    /**
     * Constructs a RelationHasA object.
     *
     * @param many
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
    public StyleMap getStyleMap() {
        StyleMap styleMap = new StyleMap();
        styleMap.addStyle("arrowtail", "vee");
        styleMap.addStyle("style", "dashed");
        if (isMany()) {
            styleMap.addStyle("headlabel", "0..*");
        }
        return styleMap;
    }

    @Override
    public String getRelKey() {
        return RelationDependsOn.REL_KEY;
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
