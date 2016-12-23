package generator;

import generator.classParser.GraphVizClassParser;
import generator.classParser.IClassModel;
import generator.classParser.IParser;
import generator.relParser.*;
import model.Relation;
import model.RelationDependsOn;
import model.RelationExtendsClass;
import model.RelationHasA;
import model.RelationImplement;

import java.util.HashMap;
import java.util.Map;

/**
 * A GraphVizGenerator that outputs DOT files for GraphViz.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public class GraphVizGenerator extends AbstractGenerator {
    public GraphVizGenerator(IGeneratorConfiguration config) {
        super(config);
    }

    @Override
    public IParser<IClassModel> createClassParser(IGeneratorConfiguration config) {
        return new GraphVizClassParser(config);
    }

    @Override
    public String createBasicConfiguration(IGeneratorConfiguration config) {
        return String.format("\tnodesep=%s;\n\t%s;\n\trankdir=%s;\n\n", config.getNodeSep(), config.getNodeStyle(),
                config.getRankDir());
    }

    @Override
    public Map<Class<? extends Relation>, IParseGuide> defineEdgeFormat(IGeneratorConfiguration config) {
        Map<Class<? extends Relation>, IParseGuide> map = new HashMap<>();
        map.put(RelationExtendsClass.class, new GraphVizSuperClassRelParser());
        map.put(RelationImplement.class, new GraphVizInterfaceParser());
        map.put(RelationHasA.class, new GraphVizHasRelParser());
        map.put(RelationDependsOn.class, new GraphVizDependsOnRelParser());
        return map;
    }
}