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
        dotString.append("\tnodesep=").append(config.getNodeSep()).append(";\n");
        dotString.append("\tnode [shape=record];\n");

        // Basic UML Boxes.
        dotString.append(classParser.parse(classes));

        // Superclass Relations
        dotString.append("\tedge [arrowhead=onormal];\n");
        // Configurable.
        dotString.append(extendsRelParser.parse(classes));

        // Inheritance Relations.
        dotString.append("\tedge [arrowhead=onormal, style=dashed];\n");
        dotString.append(implementsRelParser.parse(classes));

        // Has-A Relations.
        dotString.append("\tedge [arrowhead=vee];\n");
        dotString.append(hasRelPraser.parse(classes));

        // Depend-On Relations.
        dotString.append("\tedge [arrowhead=vee style=dashed];\n");
        dotString.append(dependsOnRelParser.parse(classes));

        dotString.append("}");

        return dotString.toString();
    }

}