package generator.relParser;

public class ReleationBijectiveDecorator implements IRelationInfo {
    private IRelationInfo decorated;

    public ReleationBijectiveDecorator(IRelationInfo rel) {
        this.decorated = rel;
    }

    public IRelationInfo getDecorated() {
        return decorated;
    }

}
