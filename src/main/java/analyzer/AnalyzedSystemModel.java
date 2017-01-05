package analyzer;

import generator.ISystemModel;
import generator.classParser.IClassModel;
import generator.relationshipParser.RelationHasABijective;
import generator.relationshipParser.ClassPair;
import generator.relationshipParser.IRelationInfo;
import generator.relationshipParser.Relation;
import generator.relationshipParser.ReleationBijectiveDecorator;
import generator.relationshipParser.RelationDependsOn;
import generator.relationshipParser.RelationHasA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

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

		// add every relation into the map
		Map<ClassPair, List<IRelationInfo>> map = new HashMap<>();
		systemModel.getRelations().forEach((r) -> {
			ClassPair pair = r.getClassPair();
			IRelationInfo info = r.getInfo();
			if (map.containsKey(pair)) {
				map.get(pair).add(info);
			} else {
				List<IRelationInfo> ls = pair.isLoop() ? new ArrayList<>() : new LinkedList<>();
				ls.add(info);
				map.put(pair, ls);
			}
		});

		// go through the map merge or remove relation according to rules
		while (!map.isEmpty()) {
			ClassPair next = map.keySet().iterator().next();
			List<IRelationInfo> a = map.get(next);
			if (next.isLoop()) {
				for (int i = 0; i < a.size(); i++) {
					for (int j = i + 1; j < a.size(); j++) {
						IRelationInfo rel = merge(a.get(i), a.get(j));
						if (rel != null) {
							a.remove(j);
							a.remove(i);
							j -= 2;
							i -= 1;
							relations.add(new Relation(next, rel));
						}
					}
				}
			} else {
				ClassPair reverse = next.reverse();
				List<IRelationInfo> b = map.getOrDefault(reverse, Collections.EMPTY_LIST);
				ListIterator<IRelationInfo> aitr = a.listIterator();
				while (aitr.hasNext()) {
					IRelationInfo aRel = aitr.next();
					ListIterator<IRelationInfo> bitr = b.listIterator();
					while (bitr.hasNext()) {
						IRelationInfo bRel = bitr.next();
						IRelationInfo rel = merge(aRel, bRel);
						if (rel != null) {
							aitr.remove();
							bitr.remove();
							relations.add(new Relation(next, rel));
						}
					}
				}
				for (IRelationInfo bRel : b)
					relations.add(new Relation(reverse, bRel));
				map.remove(reverse);
			}
			for (IRelationInfo aRel : a)
				relations.add(new Relation(next, aRel));
			map.remove(next);
		}
		return relations;
	}

	private IRelationInfo merge(IRelationInfo aRel, IRelationInfo bRel) {
		if (aRel.getClass() == bRel.getClass()) {
			if (aRel instanceof RelationDependsOn) {
				return new ReleationBijectiveDecorator(aRel);
			} else if (aRel instanceof RelationHasA) {
				return new RelationHasABijective((RelationHasA) aRel, (RelationHasA) bRel);
			}
		}
		return null;
	}

}
