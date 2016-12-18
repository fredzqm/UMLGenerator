package generator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An abstract class for Generators.
 * <p>
 * Created by lamd on 12/17/2016.
 */
public abstract class AbstractGenerator implements IGenerator {
	protected IGeneratorConfiguration config;
	IParseGuideFactory factory;
	IParser<IClassModel> classModelParser;
	Collection<IParseGuide> relParsers;

	AbstractGenerator(IGeneratorConfiguration config) {
		this.config = config;
		switch (config.getParseKey()) {
		// TODO: Add cases here.
		default:
			this.factory = new GraphVizParseGuideFactory(config.getMethodFilters());
		}

		// Setup the parsers.
		this.relParsers = new ArrayList<>();
		createParsers();
	}

	@Override
	public String generate(ISystemModel sm, Iterable<IJob> jobs) {
		// DOT parent.
		Iterable<? extends IClassModel> classes = sm.getClasses();
		StringBuilder dotString = new StringBuilder();

		// Basic Configurations.
		dotString.append(String.format("\tnodesep=%s;\n", this.config.getNodeSep()));
		dotString.append(String.format("\t%s;\n", this.config.getNodeStyle()));
		dotString.append(String.format("\trankdir=%s;\n", this.config.getRankDir()));
		dotString.append("\n");

		// Parse the class
		dotString.append(classModelParser.parse(classes) + '\n');

		// Pull the formatter from the config.
		this.relParsers.forEach((relParser) -> dotString
				.append(String.format("\t%s;\n%s\n", relParser.getEdgeStyle(), relParser.parse(classes))));

		return String.format("digraph GraphVizGeneratedDOT {\n%s}", dotString.toString());
	}

	abstract void createParsers();
}
