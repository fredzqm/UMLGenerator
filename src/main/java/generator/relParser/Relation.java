package generator.relParser;

import generator.classParser.IClassModel;

public abstract class Relation implements Comparable<Relation>{
	private final IClassModel from;
	private final IClassModel to;
	private final String key;
	
	public Relation(IClassModel from, IClassModel to) {
		this.from = from;
		this.to = to;
		this.key = getKey();
	}

	public IClassModel getFrom() {
		return from;
	}

	public IClassModel getTo() {
		return to;
	}

	private String getKey() {
		String a = getFrom().getName();
		String b = getTo().getName();
		if (a.compareTo(b) < 0)
			return a + b;
		else
			return b + a;
	}

	@Override
	public int compareTo(Relation o) {
		return key.compareTo(o.key);
	}

}
