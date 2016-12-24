package analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import generator.ISystemModel;
import generator.classParser.IClassModel;
import generator.relParser.Relation;

public class AnalyzedSystemModel implements ISystemModel {
	private ISystemModel systemModel;

	AnalyzedSystemModel(ISystemModel sm) {
		this.systemModel = sm;
	}

	@Override
	public Iterable<? extends IClassModel> getClasses() {
		return systemModel.getClasses();
	}

	@Override
	public Iterable<Relation> getRelations() {
		List<Relation> relations = new ArrayList<>();
		systemModel.getRelations().forEach(relations::add);

		Collections.sort(relations);
		// TODO: merge bidirectional edge etc.
		mergeBijectiveRelations(relations);

		return relations;
	}

	private void mergeBijectiveRelations(List<Relation> relations) {
		Relation current;
		Relation next;
		for (int i = 0; i < relations.size() - 1; i++) {
			current = relations.get(i);
			next = relations.get(i + 1);

			if (current.getClass().equals(next.getClass()) && current.getFrom().equals(next.getTo())) {
				relations.remove(next);
				current.setBijective(true);
			}
		}
	}
}
