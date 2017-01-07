package analyzer;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import generator.IGraph;
import generator.IVertex;

/**
 * An Interface for System Models.
 * <p>
 * Created by lamd on 12/9/2016.
 */
public interface ISystemModel extends IGraph {

	/**
	 * Returns an Iterable of Class Models contained within the System Model.
	 *
	 * @return Iterable of Class Models.
	 */
	Collection<? extends IClassModel> getClasses();

	/**
	 * Returns an Iterable of Relations contained within the SystemModel.
	 *
	 * @return Iterable of Relations contained within the SystemModel.
	 */
	Map<ClassPair, List<IRelationInfo>> getRelations();

	
	default Iterable<? extends IVertex> getVertice() {
		return getClasses();
	}

	default Iterable<Relation> getEdges() {
		return () -> new EdgesIterator(getRelations());
	}

	public static class EdgesIterator implements Iterator<Relation> {
		private Map<ClassPair, ? extends Iterable<IRelationInfo>> relationMap;
		private Relation cur, next, c;
		private Iterator<ClassPair> keyIterator;
		private ClassPair curClassPair;
		private Iterator<IRelationInfo> valueIterator;
		private boolean hasNext;

		public EdgesIterator(Map<ClassPair, ? extends Iterable<IRelationInfo>> map) {
			relationMap = map;
			cur = new Relation();
			next = new Relation();
			keyIterator = map.keySet().iterator();
			valueIterator = Collections.EMPTY_LIST.iterator();
			hasNext = advance();
		}

		private boolean advance() {
			c = cur;
			cur = next;
			next = c;
			while (!valueIterator.hasNext()) {
				if (!keyIterator.hasNext()) {
					return false;
				}
				curClassPair = keyIterator.next();
				valueIterator = relationMap.get(curClassPair).iterator();
			}
			IRelationInfo n = valueIterator.next();
			next.set(curClassPair, n);
			return true;
		}

		@Override
		public boolean hasNext() {
			return hasNext;
		}

		@Override
		public Relation next() {
			hasNext = advance();
			return cur;
		}

	}

}
