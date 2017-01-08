package analyzer;

import generator.IGraph;
import generator.IVertex;

import java.util.*;

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

    /**
     * Returns the vertices of the System Model. Class getClasses.
     *
     * @return Returns the classes of the System Model.
     */
    default Iterable<? extends IVertex> getVertices() {
        return getClasses();
    }

    /**
     * Returns the Iterable of Relation edges.
     *
     * @return Iterable of Relation edges.
     */
    default Iterable<Relation> getEdges() {
        return () -> new EdgesIterator(getRelations());
    }

    class EdgesIterator implements Iterator<Relation> {
        private Map<ClassPair, ? extends Iterable<IRelationInfo>> relationMap;
        private Relation cur, next, c;
        private Iterator<ClassPair> keyIterator;
        private ClassPair curClassPair;
        private Iterator<IRelationInfo> valueIterator;
        private boolean hasNext;

        EdgesIterator(Map<ClassPair, ? extends Iterable<IRelationInfo>> map) {
            relationMap = map;
            cur = new Relation();
            next = new Relation();
            keyIterator = map.keySet().iterator();
            valueIterator = Collections.EMPTY_LIST.iterator();
            hasNext = advance();
        }

        private boolean advance() {
            this.c = this.cur;
            this.cur = this.next;
            this.next = this.c;

            while (!this.valueIterator.hasNext()) {
                if (!this.keyIterator.hasNext()) {
                    return false;
                }
                this.curClassPair = this.keyIterator.next();
                this.valueIterator = this.relationMap.get(curClassPair).iterator();
            }

            this.next.set(curClassPair, valueIterator.next());
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
