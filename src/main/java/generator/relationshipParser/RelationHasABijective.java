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

	@Override
	public String getEdgeStyle() {
		RelationHasA forward = getForward();
		StringBuilder edgeBuilder = new StringBuilder("arrowhead=\"vee\" style=\"\" dir=both ");

		if (forward.isMany() || forward.getCount() > 1) {
			edgeBuilder.append("headlabel=\"1..*\" ");
		}

		RelationHasA backward = getBackward();
		if (backward.isMany() || backward.getCount() > 1) {
			edgeBuilder.append("taillabel=\"1..*\" ");
		}

		return edgeBuilder.toString();
	}

}
