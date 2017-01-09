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
}
