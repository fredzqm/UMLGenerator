package generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import generator.classParser.GraphVizClassParser;
import generator.classParser.IParser;
import generator.relParser.GraphVizDependsOnRelParser;
import generator.relParser.GraphVizHasRelParser;
import generator.relParser.GraphVizInterfaceParser;
import generator.relParser.GraphVizSuperClassRelParser;
import generator.relParser.IParseGuide;

/**
 * A GraphVizGenerator that outputs DOT files for GraphViz.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public class GraphVizGenerator extends AbstractGenerator implements IGenerator {

	public GraphVizGenerator(IGeneratorConfiguration config) {
		super(config);
	}

	@Override
	public IParser<IClassModel> createClassParser(IGeneratorConfiguration config) {
		return new GraphVizClassParser(config);
	}

	@Override
	public Collection<IParseGuide> createRelationshipParsers(IGeneratorConfiguration config) {
		return new ArrayList<>(Arrays.asList(new GraphVizSuperClassRelParser(), new GraphVizInterfaceParser(),
				new GraphVizHasRelParser(), new GraphVizDependsOnRelParser()));
	}

	@Override
	public String createBasicConfiguration(IGeneratorConfiguration config) {
		return String.format("\tnodesep=%s;\n\t%s;\n\trankdir=%s;\n\n", config.getNodeSep(), config.getNodeStyle(),
				config.getRankDir());
	}
}