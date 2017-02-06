package adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adapter.classParser.ClassParserConfiguration;
import adapter.classParser.GraphvizClassParser;
import adapter.classParser.IParser;
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
        IParser<IClassModel> parser = new GraphvizClassParser();
        IMapper<IClassModel, INode> mapper = (c) -> {
            StringBuilder sb = new StringBuilder();
            List<String> ls = new ArrayList<>();
            switch (c.getType()) {
                case INTERFACE:
                    ls.add("Interface");
                    break;
                case CONCRETE:
                    break;
                case ABSTRACT:
                    ls.add("Abstract");
                    break;
                case ENUM:
                    ls.add("Enumeration");
                    break;
            }
            ls.addAll(systemModel.getStereoTypes(c));
            for (String sterotype : ls) {
                sb.append(String.format("\\<\\<%s\\>\\>\\n", sterotype));
            }

            sb.append(parser.parse(c, classParserConfig));
            return new Node(c.getName(), sb.toString(), systemModel.getNodeStyle(c));
        };
        return mapper.map(systemModel.getClasses());
    }

    /**
     * Returns the Iterable of Relation edges.
     *
     * @return Iterable of Relation edges.
     */
    public Iterable<Relation> getEdges() {
        Map<ClassPair, Map<Class<? extends IRelationInfo>, IRelationInfo>> relations = systemModel.getRelations();
        IExpander<ClassPair, Relation> expander = (key) -> {
            IMapper<IRelationInfo, Relation> mapper = (info) -> new Relation(key, info);
            return mapper.map(relations.get(key).values());
        };
        return expander.expand(relations.keySet());
    }
}
