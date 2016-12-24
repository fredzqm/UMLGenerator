package generator;

import generator.classParser.IClassModel;
import generator.classParser.IParser;
import generator.relParser.IParseGuide;

/**
 * An abstract class for Generators.
 * <p>
 * Created by lamd on 12/17/2016.
 */
public abstract class AbstractGenerator implements IGenerator {
    private final IParser<IClassModel> classModelParser;
    private final String basicConfiguration;
    private final IParseGuide parseGuide;

    AbstractGenerator(IGeneratorConfiguration config) {
        this.classModelParser = createClassParser(config);
        this.basicConfiguration = createBasicConfiguration(config);
        this.parseGuide = createParseGuide(config);
    }

	@Override
    public String generate(ISystemModel sm) {
        // DOT parent.
        Iterable<? extends IClassModel> classes = sm.getClasses();
        StringBuilder dotString = new StringBuilder();

        // Basic Configurations.
        dotString.append(basicConfiguration);

        // Parse the class
        dotString.append(classModelParser.parse(classes)).append('\n');

        // Parse each relationship.
        sm.getRelations().forEach(relation -> {
            dotString.append(String.format("\t\"%s\" -> \"%s\" [%s];\n\n", relation.getFrom(), relation.getTo(), parseGuide.getEdgeStyle(relation.getInfo())));
        });

        return String.format("digraph GraphVizGeneratedDOT {\n%s}", dotString.toString());
    }

    /**
     * @param config
     * @return the basic configuration before all everything else
     */
    public abstract String createBasicConfiguration(IGeneratorConfiguration config);

    /**
     * Returns the class parser.
     *
     * @return ParseGuide of the Class.
     */
    public abstract IParser<IClassModel> createClassParser(IGeneratorConfiguration config);

    /**
     * Define the format of each type of relationship
     *
     * @param config
     * @return
     */
    public abstract IParseGuide createParseGuide(IGeneratorConfiguration config);
}
