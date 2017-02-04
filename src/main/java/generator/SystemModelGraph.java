package generator;

import java.util.List;
import java.util.Map;

import analyzer.utility.ClassPair;
import analyzer.utility.IRelationInfo;
import analyzer.utility.ISystemModel;
import analyzer.utility.Relation;
import utility.IExpander;
import utility.IMapper;

public class SystemModelGraph implements IGraph {
    private final ISystemModel systemModel;

    public SystemModelGraph(ISystemModel systemModel) {
        this.systemModel = systemModel;
    }

    /**
     * Returns the vertices of the System Model. Class getClasses.
     *
     * @return Returns the classes of the System Model.
     */
    public Iterable<? extends INode> getNodes() {
        return systemModel.getClasses();
    }

    /**
     * Returns the Iterable of Relation edges.
     *
     * @return Iterable of Relation edges.
     */
    public Iterable<Relation> getEdges() {
        Map<ClassPair, List<IRelationInfo>> relations = systemModel.getRelations();
        IExpander<ClassPair, Relation> expander = (key) -> {
            IMapper<IRelationInfo, Relation> mapper = (info) -> new Relation(key, info);
            return mapper.map(relations.get(key));
        };
        return expander.expand(relations.keySet());
    }
}
