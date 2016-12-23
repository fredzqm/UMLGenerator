package model;

import generator.ISystemModel;
import generator.relParser.Relation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This class representing the entire model of a java program
 */
public class SystemModel implements ISystemModel {
	private Collection<ClassModel> classList;

	private SystemModel(Collection<ClassModel> importantList) {
		this.classList = importantList;
	}

	/**
	 * constructs an instance of SystemModel given the configuration
	 * 
	 * @param config
	 * @return
	 */
	public static SystemModel getInstance(IModelConfiguration config) {
		Iterable<String> importClassesList = config.getClasses();
		if (importClassesList == null)
			throw new RuntimeException("important classes list cannot be null!");

		int recursiveFlag;
		if (config.isRecursive()) {
			recursiveFlag = ASMParser.RECURSE_INTERFACE | ASMParser.RECURSE_SUPERCLASS | ASMParser.RECURSE_HAS_A;
		} else {
			recursiveFlag = 0;
		}

		Collection<ClassModel> ls = ASMParser.getClasses(importClassesList, recursiveFlag);
		return new SystemModel(ls);
	}

	@Override
	public Iterable<ClassModel> getClasses() {
		return classList;
	}

	@Override
	public Iterable<Relation> getRelations() {
		List<Relation> ls = new ArrayList<>();
		for (ClassModel clazz : classList) {
			ClassModel superClass = clazz.getSuperClass();
			if (superClass != null)
				if (classList.contains(superClass))
					ls.add(new RelationExtendsClass(clazz, superClass));

			Iterable<ClassModel> interfaces = clazz.getInterfaces();
			for (ClassModel x : interfaces)
				if (classList.contains(x))
					ls.add(new RelationImplement(clazz, x));

			Map<ClassModel, Integer> has_a = clazz.getHasRelation();
			for (ClassModel x : has_a.keySet())
				if (classList.contains(x))
					ls.add(new RelationHasA(clazz, x, has_a.get(x)));
			
			Iterable<ClassModel> depends_on = clazz.getDependsRelation();
			for (ClassModel x : depends_on)
				if (classList.contains(x))
					ls.add(new RelationDependsOn(clazz, x));
		}
		return ls;
	}

}
