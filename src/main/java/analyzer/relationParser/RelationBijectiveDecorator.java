package analyzer.relationParser;

import analyzer.utility.StyleMap;

/**
 * RelationInfo that interprets bijectvie relations.
 */
public class RelationBijectiveDecorator extends RelationDecorator {
    /**
     * Constructs a RelationBijectiveDecorator.
     *
     * @param rel
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
        return "implements";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == RelationBijectiveDecorator.class) {
            RelationBijectiveDecorator x = (RelationBijectiveDecorator) obj;
            return x.getDecorated().equals(super.getDecorated());
        }
        return false;
    }

}
