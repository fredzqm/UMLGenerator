package generator.relParser;

public class RelationDecBidir implements IRelationInfo{
	private IRelationInfo decorated;
	
	public RelationDecBidir(IRelationInfo rel) {
		this.decorated = rel;
	}

	public IRelationInfo getDecorated() {
		return decorated;
	}
	
}
