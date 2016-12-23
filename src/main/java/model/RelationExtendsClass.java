package model;

import generator.classParser.IClassModel;
import generator.relParser.Relation;

public class RelationExtendsClass extends Relation {

	public RelationExtendsClass(IClassModel from, IClassModel to) {
		super(from, to);
	}

}
