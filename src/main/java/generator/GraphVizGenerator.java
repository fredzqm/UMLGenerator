package generator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A GraphVizGenerator that outputs DOT files for GraphViz.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public class GraphVizGenerator extends AbstractGenerator implements IGenerator {
    public GraphVizGenerator(IGeneratorConfiguration config, String parserKey) {
        super(config, parserKey);
    }

    @Override
    public String generate(ISystemModel sm, Iterable<IJob> jobs) {
        Collection<IParseGuide<IClassModel>> parsers = new ArrayList<>();

        // FIXME: Figure out how to get ride of warnings.
        parsers.add(this.factory.createClassParser());
        parsers.add(this.factory.createSuperClassParser());
        parsers.add(this.factory.createInterfaceParser());
        parsers.add(this.factory.createHasParser());
        parsers.add(this.factory.createDependsOnParser());
        parsers.addAll(this.factory.createCustomParser());

        // DOT parent.
        Iterable<? extends IClassModel> classes = sm.getClasses();
        StringBuilder dotString = new StringBuilder();

        // Basic Configurations.
        dotString.append(String.format("\tnodesep=%s;\n", this.config.getNodeSep()));
        dotString.append(String.format("\t%s;\n", this.config.getNodeStyle()));
        dotString.append(String.format("\trankdir=%s;\n", this.config.getRankDir()));
        dotString.append("\n");

        // Pull the formatter from the config.
        parsers.forEach((parseGuide) -> {
            if (parseGuide.hasEdgeStyle()) {
                dotString.append(String.format("\t%s;\n", parseGuide.getEdgeStyle()));
            }
            dotString.append(parseGuide.parse(classes));
            dotString.append("\n");
        });

        return String.format("digraph GraphVizGeneratedDOT {\n%s}", dotString.toString());
    }

}