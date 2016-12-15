package generator;

import java.util.Collection;

/**
 * A GraphVizGenerator that outputs DOT files for GraphViz.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public class GraphVizGenerator implements IGenerator {
    private IGeneratorConfiguration config;
    private IParser<IClassModel> classParser, extendsRelParser, implementsRelParser, hasRelPraser, dependsOnRelParser;

    public GraphVizGenerator(IGeneratorConfiguration config) {
        Collection<IModifier> filters = config.getFilters();
        this.config = config;

        // parsing class
        this.classParser = new GraphVizClassParser(filters);

        // parsing class relationship
        this.extendsRelParser = new GraphVizSuperClassRelParser(filters);
        this.implementsRelParser = new GraphVizInterfaceParser();
        this.hasRelPraser = new GraphVizHasRelParser(filters);
        this.dependsOnRelParser = new GraphVizDependsOnRelParser(filters);
    }

    @Override
    public String generate(ISystemModel sm, Iterable<IJob> jobs) {
        // DOT parent.
        Iterable<? extends IClassModel> classes = sm.getClasses();
        StringBuilder dotString = new StringBuilder();
        dotString.append("digraph GraphVizGeneratedDOT {\n");

        // TODO: This can be configurable.
        // Basic Configurations.
        dotString.append(String.format("\tnodesep=%s;\n", this.config.getNodeSep()));
        dotString.append("\tnode [shape=record];\n");
        dotString.append(String.format("\trankdir=%s;\n", this.config.getRankDir()));

        // Basic UML Boxes.
        dotString.append(classParser.parse(classes));

        // Superclass Relations
        dotString.append("edge [arrowhead=onormal];\n");

        dotString.append("\t").append(extendsRelParser.parse(classes));

        // Inheritance Relations.
        dotString.append("edge [arrowhead=onormal, style=dashed];\n");
        dotString.append("\t").append(implementsRelParser.parse(classes));

        // Has-A Relations.
        dotString.append("edge [arrowhead=vee];\n");
        dotString.append("\t").append(hasRelPraser.parse(classes));

        // Depend-On Relations.
        dotString.append("edge [arrowhead=vee style=dashed];\n");
        dotString.append("\t").append(dependsOnRelParser.parse(classes));

        dotString.append("\n").append("}");

        return dotString.toString();
    }

}