package analyzer.utility;

import analyzer.relationParser.RelationDecorator;

/**
 * Created by lamd on 1/26/2017.
 */
public class RelationStyleDecorator extends RelationDecorator {
    private final String edgeStyle;

    public RelationStyleDecorator(IRelationInfo info, String edgeStyle) {
        super(info);
        this.edgeStyle = edgeStyle;
    }

    @Override
    public String toString() {
        return "favorCom-" + super.toString();
    }

    @Override
    public String getBaseEdgeStyle() {
        return super.getBaseEdgeStyle() + " " + this.edgeStyle;
    }
}
