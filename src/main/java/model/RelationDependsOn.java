package model;

import generator.classParser.IClassModel;
import generator.relParser.Relation;

public class RelationDependsOn extends Relation {

	public RelationDependsOn(IClassModel from, IClassModel to) {
		super(from, to);
	}

}
