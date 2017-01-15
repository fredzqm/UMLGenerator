package analyzer;

import generator.IGraph;
import generator.INode;
import utility.IExpander;
import utility.IMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    default Iterable<? extends INode> getVertices() {
        return getClasses();
    }

    /**
     * Returns the Iterable of Relation edges.
     *
     * @return Iterable of Relation edges.
     */
    default Iterable<Relation> getEdges() {
        Map<ClassPair, List<IRelationInfo>> relations = getRelations();
        IExpander<ClassPair, Relation> expander = (key) -> {
            IMapper<IRelationInfo, Relation> mapper = (info) -> new Relation(key, info);
            return mapper.map(relations.get(key));
        };
        return expander.expand(relations.keySet());
    }

}
