package model;

import generator.relParser.Relation;

public class RelationHasA extends Relation {

	private boolean many;
	private int count;

	public RelationHasA(ClassModel from, ClassModel to, int count) {
		super(from, to);
		many = count <= 0;
		count = Math.abs(count);
	}

	public boolean isMany() {
		return many;
	}

	public int getCount() {
		return count;
	}

}
