package generator;

import generator.classParser.GraphVizClassParser;
import generator.classParser.IClassModel;
import generator.classParser.IParser;

/**
 * A GraphVizGenerator that outputs DOT files for GraphViz.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public class GraphVizGenerator extends AbstractGenerator {
	
    @Override
    public IParser<IClassModel> createClassParser(IGeneratorConfiguration config) {
        return new GraphVizClassParser(config);
    }

    @Override
    public String createBasicConfiguration(IGeneratorConfiguration config) {
        return String.format("\tnodesep=%s;\n\t%s;\n\trankdir=%s;\n\n", config.getNodeSep(), config.getNodeStyle(),
                config.getRankDir());
    }

}