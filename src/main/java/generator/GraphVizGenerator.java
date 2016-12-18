package generator;

import utility.IFilter;
import utility.MethodType;
import utility.Modifier;

/**
 * A GraphVizGenerator that outputs DOT files for GraphViz.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public class GraphVizGenerator implements IGenerator {
	private IGeneratorConfiguration config;
	private IParser<IClassModel> classParser, extendsRelParser, implementsRelParser, hasRelPraser, dependsOnRelParser;

	public GraphVizGenerator(IGeneratorConfiguration config) {
		IFilter<Modifier> filters = config.getModifierFilter();
		this.config = config;

		// parsing class
		this.classParser = new GraphVizClassParser(filters, (d) -> true, (d) -> true);

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

		// TODO: This can be configurable.
		// Basic Configurations.
		dotString.append(String.format("\tnodesep=%s;\n", config.getNodeSep()));
		dotString.append("\tnode [shape=record];\n");
		dotString.append(String.format("\trankdir=%s;\n", config.getRankDir()));
		dotString.append("\n");

		// Basic UML Boxes.
		dotString.append(classParser.parse(classes));
		dotString.append("\n");

		// Superclass Relations
		dotString.append("\tedge [arrowhead=onormal];\n");
		dotString.append(extendsRelParser.parse(classes));
		dotString.append("\n");

		// Inheritance Relations.
		dotString.append("\tedge [arrowhead=onormal, style=dashed];\n");
		dotString.append(implementsRelParser.parse(classes));
		dotString.append("\n");

		// Has-A Relations.
		dotString.append("\tedge [arrowhead=vee];\n");
		dotString.append(hasRelPraser.parse(classes));
		dotString.append("\n");

		// Depend-On Relations.
		dotString.append("\tedge [arrowhead=vee style=dashed];\n");
		dotString.append(dependsOnRelParser.parse(classes));

		return String.format("digraph GraphVizGeneratedDOT {\n%s}", dotString.toString());
	}

}