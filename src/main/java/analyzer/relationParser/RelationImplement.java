package analyzer.relationParser;

import analyzer.utility.StyleMap;

/**
 * RelationInfo that interprets interfaces.
 */
public class RelationImplement implements IRelationInfo {
    @Override
    public String toString() {
        return "Implements";
    }

    @Override
    public StyleMap getStyleMap() {
        StyleMap styleMap = new StyleMap();
        styleMap.addStyle("arrowtail", "onormal");
        styleMap.addStyle("style", "dashed");
        return styleMap;
    }

    @Override
    public String getRelKey() {
        return "Implements";
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == RelationImplement.class;
    }

    @Override
    public int hashCode() {
        return 42;
    }
}
