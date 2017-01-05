package generator.relationshipParser;

public class RelationHasA implements IRelationInfo {
    private final boolean many;
    private final int count;

    public RelationHasA(int count) {
        this.many = count > 1;
        this.count = Math.abs(count);
    }

    public boolean isMany() {
        return this.many;
    }

    public int getCount() {
        return this.count;
    }
}
