package analyzerRelationParser;

import analyzer.IRelationInfo;

/**
 * RelationInfo that interprets depends-on relation.
 */
public class RelationDependsOn implements IRelationInfo {
    @Override
    public String toString() {
        return "Depends on";
    }

    @Override
    public String getEdgeStyle() {
        return "arrowhead=\"vee\" style=dashed ";
    }
}
