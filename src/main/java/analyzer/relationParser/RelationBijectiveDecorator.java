package analyzer.relationParser;

import analyzer.utility.IRelationInfo;

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
    public String getBaseEdgeStyle() {
        return super.getBaseEdgeStyle() + "arrowtail=\"vee\" dir=both ";
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
