package generator;

import config.Configuration;
import generator.classParser.IClassModel;
import generator.classParser.IParser;

/**
 * An abstract class for Generators.
 * <p>
 * Created by lamd on 12/17/2016.
 */
public abstract class AbstractGenerator implements IGenerator {

	@Override
	public String generate(IGeneratorConfiguration config, ISystemModel sm) {
		// DOT parent.
		Iterable<? extends IClassModel> classes = sm.getClasses();
		StringBuilder dotString = new StringBuilder();

		// Basic Configurations.
		dotString.append(createBasicConfiguration(config));

		// Parse the class
		dotString.append(createClassParser(config).parse(classes)).append('\n');

		// Parse each relationship.
		Iterable<? extends IRelation> relations = sm.getRelations();
		relations.forEach(relation -> {
			dotString.append(String.format("\t\"%s\" -> \"%s\" [%s];\n", relation.getFrom(), relation.getTo(),
					relation.getEdgeStyle()));
		});

		return String.format("digraph GraphVizGeneratedDOT {\n%s}", dotString.toString());
	}

	/**
	 * Returns the String of the Basic Configuration.
	 *
	 * @param config
	 * @return String of the Basic Configuration
	 */
	public abstract String createBasicConfiguration(IGeneratorConfiguration config);

	/**
	 * Returns the class parser.
	 *
	 * @return GraphVizParseGuide of the Class.
	 */
	public abstract IParser<IClassModel> createClassParser(IGeneratorConfiguration config);

}
