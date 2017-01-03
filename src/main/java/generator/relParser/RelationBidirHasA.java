package generator.relParser;

public class RelationBidirHasA implements IRelationInfo {

	private RelationHasA a, b;

	public RelationBidirHasA(RelationHasA aRel, RelationHasA bRel) {
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
