package analyzerRelationParser;

public class RelationDependsOn implements IRelationInfo {
	
	@Override
	public String toString() {
		return "Depends on";
	}

	@Override
	public String getEdgeStyle() {
        return "arrowhead=\"vee\" style=dashed ";
	}
}
