package generator.relationshipParser;

public class RelationHasABijective implements IRelationInfo {

	private RelationHasA a, b;

	public RelationHasABijective(RelationHasA aRel, RelationHasA bRel) {
		this.a = aRel;
		this.b = bRel;
	}

	RelationHasA getForward() {
		return a;
	}

	RelationHasA getBackward() {
		return b;
	}

	@Override
	public String toString() {
		return "" + a + " & " + b;
	}
}
