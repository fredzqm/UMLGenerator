package analyzerRelationParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import analyzer.IASystemModel;
import analyzer.IClassModel;
import analyzer.SystemModelFiler;

public class MergeRelationSystemModel extends SystemModelFiler{
	MergeRelationSystemModel(IASystemModel sm) {
		super(sm);
	}

	public Iterable<Relation> getRelationPure() {
		List<Relation> ls = new ArrayList<>();
		Collection<? extends IClassModel> classList = getClasses();
		for (IClassModel classModel : classList) {
			String className = classModel.getName();

			// add related super class relationship
			IClassModel superClass = classModel.getSuperClass();
			if (superClass != null)
				if (classList.contains(superClass))
					ls.add(new Relation(new ClassPair(className, superClass.getName()), new RelationExtendsClass()));

			// add related interface relationship
			Iterable<? extends IClassModel> interfaces = classModel.getInterfaces();
			for (IClassModel x : interfaces)
				if (classList.contains(x))
					ls.add(new Relation(new ClassPair(className, x.getName()), new RelationImplement()));

			// add related has-a relationship
			Map<? extends IClassModel, Integer> has_a = classModel.getHasRelation();
			for (IClassModel x : has_a.keySet())
				if (classList.contains(x))
					ls.add(new Relation(new ClassPair(className, x.getName()), new RelationHasA(has_a.get(x))));

			// add related depends on relationship
			Iterable<? extends IClassModel> depends_on = classModel.getDependsRelation();
			for (IClassModel x : depends_on)
				if (classList.contains(x))
					ls.add(new Relation(new ClassPair(className, x.getName()), new RelationDependsOn()));
		}
		return ls;
	}
	
	@Override
	public Iterable<Relation> getRelations() {
		List<Relation> relations = new ArrayList<>();

		// add every relation into the map
		Map<ClassPair, List<IRelationInfo>> map = new HashMap<>();
		getRelationPure().forEach((r) -> {
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
