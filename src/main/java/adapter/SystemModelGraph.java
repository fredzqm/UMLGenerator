package adapter;

import java.util.List;
import java.util.Map;

import adapter.classParser.ClassParserConfiguration;
import analyzer.utility.ClassPair;
import analyzer.utility.IClassModel;
import analyzer.utility.IRelationInfo;
import analyzer.utility.ISystemModel;
import config.IConfiguration;
import generator.IGraph;
import generator.INode;
import utility.IExpander;
import utility.IMapper;

public class SystemModelGraph implements IGraph {
    private final ISystemModel systemModel;
    private ClassParserConfiguration classParserConfig;

    public SystemModelGraph(ISystemModel systemModel, IConfiguration config) {
        this.systemModel = systemModel;
        this.classParserConfig = config.createConfiguration(ClassParserConfiguration.class);
    }

    /**
     * Returns the vertices of the System Model. Class getClasses.
     *
     * @return Returns the classes of the System Model.
     */
    public Iterable<? extends INode> getNodes() {
        IMapper<IClassModel, INode> mapper = (c) -> new ClassModelNode(c, classParserConfig);
        return mapper.map(systemModel.getClasses());
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
