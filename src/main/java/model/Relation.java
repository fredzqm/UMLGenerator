package model;

import generator.classParser.IClassModel;
import generator.relParser.IRelation;

/**
 * 
 * Relation represet
 * 
 * @author zhang
 *
 */
public abstract class Relation implements IRelation, Comparable<IRelation> {
	private final IClassModel from;
	private final IClassModel to;

	public Relation(IClassModel from, IClassModel to) {
		this.to = to;
		this.from = from;
	}

	public String getFromName() {
		return this.from.getName();
	}

	public String getToName() {
		return this.to.getName();
	}

	public IClassModel getFrom() {
		return this.from;
	}

	public IClassModel getTo() {
		return this.to;
	}

	private static String getKey(IRelation x) {
		String fromName = x.getFromName();
		String toName = x.getToName();
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
