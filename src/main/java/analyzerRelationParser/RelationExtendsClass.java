package analyzerRelationParser;

import analyzer.IRelationInfo;

/**
 * RelationInfo that interprets Extends relations.
 */
public class RelationExtendsClass implements IRelationInfo {
    @Override
    public String toString() {
        return "Extends";
    }

    @Override
    public String getEdgeStyle() {
        return "arrowhead=\"onormal\" style=\"\" ";
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == RelationExtendsClass.class;
    }

    @Override
    public int hashCode() {
        return 52;
    }
}
