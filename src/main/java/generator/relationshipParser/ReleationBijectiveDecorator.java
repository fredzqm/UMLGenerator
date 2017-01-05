package generator.relationshipParser;

public class ReleationBijectiveDecorator implements IRelationInfo {
    private IRelationInfo decorated;

    public ReleationBijectiveDecorator(IRelationInfo rel) {
        this.decorated = rel;
    }

    IRelationInfo getDecorated() {
        return decorated;
    }
    
	@Override
	public String toString() {
		return "bir-" + decorated.toString();
	}

}
