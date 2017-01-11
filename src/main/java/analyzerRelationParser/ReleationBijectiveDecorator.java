package analyzerRelationParser;

import analyzer.IRelationInfo;

/**
 * RelationInfo that interprets bijectvie relations.
 */
public class ReleationBijectiveDecorator implements IRelationInfo {
    private IRelationInfo decorated;

    /**
     * Constructs a RelationBijectiveDecorator.
     *
     * @param rel
     */
    ReleationBijectiveDecorator(IRelationInfo rel) {
        this.decorated = rel;
    }

    /**
     * Returns the RelationInfo it decorates.
     *
     * @return IRelationInfo decorated.
     */
    IRelationInfo getDecorated() {
        return decorated;
    }

    @Override
    public String toString() {
        return "bir-" + decorated.toString();
    }

    @Override
    public String getEdgeStyle() {
        return getDecorated().getEdgeStyle() + "arrowtail=\"vee\" style=\"\" dir=both ";
    }
}
