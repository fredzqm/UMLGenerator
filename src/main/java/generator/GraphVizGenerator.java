package generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A GraphVizGenerator that outputs DOT files for GraphViz.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public class GraphVizGenerator implements IGenerator {
	private List<GraphVizClass> classes;

	private StringBuilder dotString;

	public GraphVizGenerator() {
		this.classes = new ArrayList<>();
		this.dotString = new StringBuilder();
	}

	public List<GraphVizClass> getClasses() {
		return this.classes;
	}

	private void parseSystemModel(IGeneratorSystemModel sm, Collection<IModifier> filters) {
		Iterable<? extends IClassModel> classes = sm.getClasses();
		classes.forEach((model) -> this.classes.add(new GraphVizClass(model, filters)));
	}

	private void createDotString(IGeneratorConfiguration config) {
		// DOT parent.
		this.dotString.append("digraph GraphVizGeneratedDOT {\n");

		// TODO: This can be configurable.
		// Basic Configurations.
		this.dotString.append("\tnodesep=").append(config.getNodeSep()).append(";\n");
		this.dotString.append("\tnode [shape=record];\n");

		// Basic UML Boxes.
		this.classes.forEach((vizClass) -> {
			this.dotString.append(vizClass.getClassVizDescription());
			this.dotString.append("\n");
		});

		// Superclass Relations
		this.dotString.append("\tedge [arrowhead=onormal];\n");
		// Configurable.
		this.classes.forEach((vizClass) -> {
			this.dotString.append("\t");
			this.dotString.append(vizClass.getSuperClassVizDescription());
			this.dotString.append("\n");
		});

		// Inheritance Relations.
		this.dotString.append("\tedge [arrowhead=onormal, style=dashed];\n");
		this.classes.forEach((vizClass) -> {
			this.dotString.append("\t");
			this.dotString.append(vizClass.getInterfaceVizDescription());
			this.dotString.append("\n");
		});

		// Has-A Relations.
		this.dotString.append("\tedge [arrowhead=vee];\n");
		this.classes.forEach((vizClass) -> {
			this.dotString.append("\t");
			this.dotString.append(vizClass.getHasRelationVizDescription());
			this.dotString.append("\n");
		});

		// Depend-On Relations.
		this.dotString.append("\tedge [arrowhead=vee style=dashed];\n");
		this.classes.forEach((vizClass) -> {
			this.dotString.append("\t");
			this.dotString.append(vizClass.getDependsRelationVizDescription());
			this.dotString.append("\n");
		});

		this.dotString.append("}");
	}

	@Override
	public String generate(IGeneratorSystemModel sm, IGeneratorConfiguration config, Iterable<IJob> jobs) {
		parseSystemModel(sm, config.getFilters());
		createDotString(config);
		return this.dotString.toString();
	}


}