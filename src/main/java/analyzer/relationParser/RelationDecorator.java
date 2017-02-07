package analyzer.relationParser;

import analyzer.utility.StyleMap;

/**
 * A Decorator for Relation classes.
 * <p>
 * Created by lamd on 1/26/2017.
 */
public abstract class RelationDecorator implements IRelationInfo {
    private IRelationInfo decorated;

    /**
     * Constructs a RelationBijectiveDecorator.
     *
     * @param decorated Decorated IRelationInfo.
     */
    public RelationDecorator(IRelationInfo decorated) {
        this.decorated = decorated;
    }

    /**
     * Returns the RelationInfo it decorates.
     *
     * @return IRelationInfo decorated.
     */
    IRelationInfo getDecorated() {
        return this.decorated;
    }

    @Override
    public String getRelKey() {
        return this.decorated.getRelKey();
    }

    @Override
    public String toString() {
        return this.decorated.toString();
    }

    @Override
    public StyleMap getStyleMap() {
        return decorated.getStyleMap();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RelationDecorator) {
            RelationDecorator x = RelationDecorator.class.cast(obj);
            return x.getDecorated().equals(this.decorated);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.decorated.hashCode();
    }
}
