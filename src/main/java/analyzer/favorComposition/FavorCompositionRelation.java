package analyzer.favorComposition;

import analyzer.relationParser.RelationDecorator;
import analyzer.utility.IRelationInfo;

/**
 * FavorComposoition Analyzer's Relation Class.
 * <p>
 * Created by lamd on 1/26/2017.
 */
public class FavorCompositionRelation extends RelationDecorator {
    private final FavorCompositionConfiguration config;

    FavorCompositionRelation(IRelationInfo info, FavorCompositionConfiguration config) {
        super(info);
        this.config = config;
    }

    @Override
    public String toString() {
        return "favorCom-" + super.toString();
    }

    @Override
    public String getEdgeStyle() {
        return String.format("%s color=\"%s\"", super.getEdgeStyle(), this.config.getFavorComColor());
    }
}
