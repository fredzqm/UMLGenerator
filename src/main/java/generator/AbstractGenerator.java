package generator;

/**
 * An abstract class for Generators.
 * <p>
 * Created by lamd on 12/17/2016.
 */
public abstract class AbstractGenerator implements IGenerator {

	@Override
	public String generate(IGeneratorConfiguration config, ISystemModel sm) {
		// DOT parent.
		StringBuilder dotString = new StringBuilder();

		// Basic Configurations.
		dotString.append(createBasicConfiguration(config));

		// render the classes
		Iterable<? extends IVertex> vertece = sm.getClasses();
		vertece.forEach((vertex) -> {
			dotString.append(
					String.format("\t\"%s\" [\n\t\tlabel = \"{%s}\"\n\t];\n", vertex.getName(), vertex.getLabel()));
		});

		// Parse each relationship.
		Iterable<? extends IEdge> relations = sm.getRelations();
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

}
