package analyzerRelationParser;

public class RelationImplement implements IRelationInfo {
	@Override
	public String toString() {
		return "Implements";
	}

	@Override
	public String getEdgeStyle() {
		return "arrowhead=\"onormal\" style=dashed ";
	}
}
