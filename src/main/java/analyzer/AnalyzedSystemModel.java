package analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import generator.ISystemModel;
import generator.classParser.IClassModel;
import generator.relParser.Relation;

public class AnalyzedSystemModel implements ISystemModel {
	private ISystemModel sysModel;

	AnalyzedSystemModel(ISystemModel sm) {
		sysModel = sm;
	}

	@Override
	public Iterable<? extends IClassModel> getClasses() {
		return sysModel.getClasses();
	}

	@Override
	public Iterable<Relation> getRelations() {
		List<Relation> ls = new ArrayList<>();
		sysModel.getRelations().forEach(ls::add);

		Collections.sort(ls);
		// TODO: merge bidirectional edge etc.

		return ls;
	}

}
