package generator;

import utility.IFilter;
import utility.Modifier;

/**
 * A GraphVizGenerator that outputs DOT files for GraphViz.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public class GraphVizGenerator implements IGenerator {
    private IGeneratorConfiguration config;
//    private IParser<IClassModel> classParser, extendsRelParser, implementsRelParser, hasRelPraser, dependsOnRelParser;

    public GraphVizGenerator(IGeneratorConfiguration config) {
        IFilter<Modifier> filters = config.getFilters();
        this.config = config;

        // parsing class
//        IParser<IClassModel> classParser = new GraphVizClassParser(filters, data -> true, method -> true);
//
//        // parsing class relationship
//        this.extendsRelParser = new GraphVizSuperClassRelParser(filters);
//        this.implementsRelParser = new GraphVizInterfaceParser();
//        this.hasRelPraser = new GraphVizHasRelParser(filters);
//        this.dependsOnRelParser = new GraphVizDependsOnRelParser(filters);
    }

    @Override
    public String generate(ISystemModel sm, Iterable<IJob> jobs) {
        // DOT parent.
        Iterable<? extends IClassModel> classes = sm.getClasses();
        StringBuilder dotString = new StringBuilder();

        // Basic Configurations.
        dotString.append(String.format("\tnodesep=%s;\n", config.getNodeSep()));
        dotString.append(String.format("\t%s;\n", config.getNodeStyle()));
        dotString.append(String.format("\trankdir=%s;\n", config.getRankDir()));
        dotString.append("\n");

        // Pull the formatter from the config.
        IFormat format = config.getFormat();
        format.getFormatters().forEach((formatter) -> {
            if (formatter.hasEdgeStyle()) {
                dotString.append(String.format("\t%s;\n", formatter.getEdgeStyle()));
            }
            dotString.append(formatter.parse(classes));
            dotString.append("\n");
        });

//        // Basic UML Boxes.
//        dotString.append(classParser.parse(classes));
//        dotString.append("\n");
//
//        // Superclass Relations
//        dotString.append(String.format("\tedge [%s];\n", format.superclassEdgeStyle()));
//        dotString.append(extendsRelParser.parse(classes));
//        dotString.append("\n");
//
//        // Inheritance Relations.
//        dotString.append(String.format("\tedge [%s];\n", format.getInheritanceEdgeStyle()));
//        dotString.append(implementsRelParser.parse(classes));
//        dotString.append("\n");
//
//        // Has-A Relations.
//        dotString.append(String.format("\tedge [%s];\n", format.getHasAEdgeStyle()));
//        dotString.append(hasRelPraser.parse(classes));
//        dotString.append("\n");
//
//        // Depend-On Relations.
//        dotString.append(String.format("\tedge [%s];\n", format.getDependOnEdgeStyle()));
//        dotString.append(dependsOnRelParser.parse(classes));
//        dotString.append("\n");


        return String.format("digraph GraphVizGeneratedDOT {\n%s}", dotString.toString());
    }

}