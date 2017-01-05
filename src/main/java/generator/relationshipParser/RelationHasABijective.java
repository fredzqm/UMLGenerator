package generator.relationshipParser;

public class RelationHasABijective implements IRelationInfo {

    private RelationHasA a, b;

    public RelationHasABijective(RelationHasA aRel, RelationHasA bRel) {
        this.a = aRel;
        this.b = bRel;
    }

    public RelationHasA getForward() {
        return a;
    }

    public RelationHasA getBackward() {
        return b;
    }
}
