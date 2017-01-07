package generator;

import generator.classParser.IClassModel;
import generator.classParser.IParser;

/**
 * An abstract class for Generators.
 * <p>
 * Created by lamd on 12/17/2016.
 */
public abstract class AbstractGenerator implements IGenerator {
    private final IParser<IClassModel> classModelParser;
    private final String basicConfiguration;

    AbstractGenerator(IGeneratorConfiguration config) {
        this.classModelParser = createClassParser(config);
        this.basicConfiguration = createBasicConfiguration(config);
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
        Iterable<? extends IRelation> relations = sm.getRelations();
        relations.forEach(relation -> {
            dotString.append(String.format("\t\"%s\" -> \"%s\" [%s];\n", relation.getFrom(), relation.getTo(), relation.getEdgeStyle()));
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
