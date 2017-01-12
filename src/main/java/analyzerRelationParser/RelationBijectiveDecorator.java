package analyzerRelationParser;

import analyzer.IRelationInfo;

/**
 * RelationInfo that interprets bijectvie relations.
 */
public class RelationBijectiveDecorator implements IRelationInfo {
    private IRelationInfo decorated;

    /**
     * Constructs a RelationBijectiveDecorator.
     *
     * @param rel
     */
    RelationBijectiveDecorator(IRelationInfo rel) {
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
        return getDecorated().getEdgeStyle() + "arrowtail=\"vee\" dir=both ";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == RelationBijectiveDecorator.class) {
            RelationBijectiveDecorator x = (RelationBijectiveDecorator) obj;
            return x.decorated.equals(this.decorated);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return decorated.hashCode();
    }
}
