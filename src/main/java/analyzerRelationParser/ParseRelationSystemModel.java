package analyzerRelationParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import analyzer.ClassPair;
import analyzer.IClassModel;
import analyzer.IFieldModel;
import analyzer.IRelationInfo;
import analyzer.ISystemModel;
import analyzer.ISystemModelFilter;
import analyzer.ITypeModel;
import utility.IFilter;
import utility.IMapper;

/**
 * It decorates ISystem model and supplies extends, implements, has-a and
 * depends-on relationship
 * 
 */
public class ParseRelationSystemModel extends ISystemModelFilter {
	/**
	 * Construct ParseRelationSystemModel.
	 *
	 * @param systemModel
	 */
	ParseRelationSystemModel(ISystemModel systemModel) {
		super(systemModel);
	}

	public Map<ClassPair, List<IRelationInfo>> getRelations() {
		Map<ClassPair, List<IRelationInfo>> map = new HashMap<>();
		Collection<? extends IClassModel> classList = getClasses();
		for (IClassModel classModel : classList) {
			// add related super class relationship
			IClassModel superClass = classModel.getSuperClass();
			if (superClass != null)
				if (classList.contains(superClass))
					addToMap(map, new ClassPair(classModel, superClass), new RelationExtendsClass());
			
			// add related interface relationship
			Iterable<? extends IClassModel> interfaces = classModel.getInterfaces();
			for (IClassModel x : interfaces)
				if (classList.contains(x))
					addToMap(map, new ClassPair(classModel, x), new RelationImplement());

			// add related has-a relationship
			Map<? extends IClassModel, Integer> has_a = getHasRelationship(classModel);
			for (IClassModel x : has_a.keySet())
				if (classList.contains(x))
					addToMap(map, new ClassPair(classModel, x), new RelationHasA(has_a.get(x)));

			// add related depends on relationship
			Iterable<? extends IClassModel> depends_on = classModel.getClassDependsOn();
			for (IClassModel x : depends_on)
				if (classList.contains(x))
					addToMap(map, new ClassPair(classModel, x), new RelationDependsOn());
		}
		return map;
	}

	private Map<IClassModel, Integer> getHasRelationship(IClassModel classModel) {
		HashMap<IClassModel, Integer> hasARel = new HashMap<>();
		Set<IClassModel> hasMany = new HashSet<>();
		IFilter<IFieldModel> filter = (f) -> !f.isStatic();
		IMapper<IFieldModel, ITypeModel> mapper = IFieldModel::getFieldType;
		for (ITypeModel hasType : mapper.map(filter.filter(classModel.getFields()))) {
			IClassModel hasClass = hasType.getClassModel();
			if (hasType.getDimension() > 0) {
				if (hasClass != null)
					hasMany.add(hasClass);
			}
			ITypeModel collection = hasType.assignTo("java.lang.Iterable");
			if (collection != null && collection.getGenericArgLength() > 0) {
				// iterable of other things
				IClassModel hasManyClass = collection.getGenericArg(0).getClassModel();
				if (hasManyClass != null)
					hasMany.add(hasManyClass);
			}
			if (hasClass != null) {
				// regular fields
				if (hasARel.containsKey(hasClass)) {
					hasARel.put(hasClass, hasARel.get(hasClass) + 1);
				} else {
					hasARel.put(hasClass, 1);
				}
			}
		}
		for (IClassModel c : hasMany) {
			if (hasARel.containsKey(c)) {
				hasARel.put(c, -hasARel.get(c));
			} else {
				hasARel.put(c, 0);
			}
		}
		return hasARel;
	}

	private void addToMap(Map<ClassPair, List<IRelationInfo>> map, ClassPair pair, IRelationInfo info) {
		if (map.containsKey(pair)) {
			map.get(pair).add(info);
		} else {
			List<IRelationInfo> ls2 = pair.isLoop() ? new ArrayList<>() : new LinkedList<>();
			ls2.add(info);
			map.put(pair, ls2);
		}
	}
}
