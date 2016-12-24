package generator.relParser;

public abstract class RelationDecorator implements IRelation {
	private IRelation relation;

	public RelationDecorator(IRelation decorated) {
		relation = decorated;
	}

	public IRelation getDecorated() {
		return relation;
	}

	@Override
	public String getTo() {
		return relation.getTo();
	}

	@Override
	public String getFrom() {
		return relation.getFrom();
	}

	@Override
	public int compareTo(IRelation o) {
		return relation.compareTo(o);
	}

}
