package analyzer.relationParser;

import analyzer.utility.StyleMap;

/**
 * RelationInfo that interprets bijectvie relations.
 */
public class RelationBijectiveDecorator extends RelationDecorator {
    public static final String REL_KEY = "bijective_relation";

    /**
     * Constructs a RelationBijectiveDecorator.
     *
     * @param decorated
     */
    RelationBijectiveDecorator(IRelationInfo decorated) {
        super(decorated);
    }

    @Override
    public String toString() {
        return "bir-" + super.toString();
    }

    @Override
    public StyleMap getStyleMap() {
        StyleMap styleMap = super.getStyleMap();
        styleMap.addStyle("arrowtail", "vee");
        styleMap.addStyle("dir", "both");
        return styleMap;
    }

    @Override
    public String getRelKey() {
        return RelationBijectiveDecorator.REL_KEY;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RelationBijectiveDecorator) {
            RelationBijectiveDecorator x = RelationBijectiveDecorator.class.cast(obj);
            return x.getDecorated().equals(super.getDecorated());
        }
        return false;
    }

}
