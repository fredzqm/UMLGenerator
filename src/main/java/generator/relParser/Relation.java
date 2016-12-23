package generator.relParser;

import generator.classParser.IClassModel;

/**
 * 
 * Relation represet
 * 
 * @author zhang
 *
 */
public abstract class Relation implements IRelation, Comparable<IRelation> {
	private final String from;
	private final String to;

	public Relation(String from, String to) {
		this.to = to;
		this.from = from;
	}

	@Override
	public String getFrom() {
		return from;
	}

	@Override
	public String getTo() {
		return to;
	}

	private static String getKey(IRelation x) {
		String fromName = x.getFrom();
		String toName = x.getTo();
		if (fromName.compareTo(toName) < 0)
			return fromName + toName;
		else
			return toName + fromName;
	}

	@Override
	public int compareTo(IRelation o) {
		return getKey(this).compareTo(getKey(o));
	}

}
