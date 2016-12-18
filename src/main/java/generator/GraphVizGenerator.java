package generator;

import java.util.ArrayList;
import java.util.Collection;

import generator.parser.GraphVizClassParser;
import generator.parser.GraphVizDependsOnRelParser;
import generator.parser.GraphVizHasRelParser;
import generator.parser.GraphVizInterfaceParser;
import generator.parser.GraphVizSuperClassRelParser;
import utility.IFilter;
import utility.Modifier;

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
		return new GraphVizClassParser(config.getModifierFilters(), (data) -> true, (method) -> true);
	}

	@Override
	public Collection<IParseGuide> createRelationshipParsers(IGeneratorConfiguration config) {
		IFilter<Modifier> filters = config.getModifierFilters();
		
		Collection<IParseGuide> relationshipParsers = new ArrayList<>();

		IParser<IClassModel> extendsRelParser = new GraphVizSuperClassRelParser(filters);
		IParser<IClassModel> implementsRelParser = new GraphVizInterfaceParser();
		IParser<IClassModel> hasRelPraser = new GraphVizHasRelParser(filters);
		IParser<IClassModel> dependsOnRelParser = new GraphVizDependsOnRelParser(filters);

		relationshipParsers.add(new GraphVizParseGuide(extendsRelParser, "edge [arrowhead=onormal]"));
		relationshipParsers.add(new GraphVizParseGuide(implementsRelParser, "edge [arrowhead=onormal, style=dashed]"));
		relationshipParsers.add(new GraphVizParseGuide(hasRelPraser, "edge [arrowhead=vee]"));
		relationshipParsers.add(new GraphVizParseGuide(dependsOnRelParser, "edge [arrowhead=vee style=dashed]"));

		return relationshipParsers;
	}

	@Override
	public String createBasicConfiguration(IGeneratorConfiguration config) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("\tnodesep=%s;\n", config.getNodeSep()));
		sb.append(String.format("\t%s;\n", config.getNodeStyle()));
		sb.append(String.format("\trankdir=%s;\n", config.getRankDir()));
		sb.append("\n");
		return sb.toString();
	}
}