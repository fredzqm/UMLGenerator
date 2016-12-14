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

	/**
	 * An Inner class that represents a single class in the DOT language.
	 */
	private class GraphVizClass {
		private String name;
		private IClassModel model;
		private IGraphVizHeader headerParser;
		private IParser<IFieldModel> fieldParser;
		private IParser<IMethodModel> methodParser;
		private IRelationParser extendsRel, implementsRel;

		private IGraphVizParser hasRelation, dependsOn;
		
		private GraphVizClass(IClassModel model, Collection<IModifier> filters) {
			this.name = model.getName();
			this.model = model;

			this.headerParser = new GraphVizHeaderParser(model.getType(), this.name);

			// class properties
			this.fieldParser = new GraphVizFieldParser(filters);
			this.methodParser = new GraphVizMethodParser(filters);

			// class relationship
			this.extendsRel = new GraphizExtendsRelParser(filters);
			this.implementsRel = new GraphVizInterfaceParser();
			// TODO: has not refactor yet
			this.hasRelation = new GraphVizHasParser(model.getHasRelation(), filters, this.name);
			this.dependsOn = new GraphVizDependsOnParser(model.getDependsRelation(), filters, this.name);
		}

		/**
		 * Returns the String of the sterotype.
		 * <p>
		 * Example: <<Interface>> or <<Abstract>>
		 *
		 * @return Class' Sterotype.
		 */
		public String getSteroType() {
			return this.headerParser.getStereoType();
		}

		/**
		 * Returns the name of to be displayed on the UML.
		 *
		 * @return Display name.
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * Returns String of the header information. It is a composite of the
		 * sterotype and name.
		 *
		 * @return Sterotype and Name.
		 */
		public String getHeader() {
			return this.headerParser.getOutput();
		}

		/**
		 * Returns String of the Fields in DOT file format.
		 *
		 * @return Fields DOT format.
		 */
		public String getFields() {
			return fieldParser.parse(model.getFields());
		}

		/**
		 * Returns String of the methods in DOT file format.
		 *
		 * @return Methods DOT format.
		 */
		public String getMethods() {
			return methodParser.parse(model.getMethods());
		}

		/**
		 * Returns String of the interfaces in DOT file format.
		 *
		 * @return Interfaces DOT format.
		 */
		String getInterfaceVizDescription() {
			return this.implementsRel.parse(model, model.getInterfaces());
		}

		/**
		 * Returns the String of the Has-A relationship of the class in DOT file
		 * format.
		 *
		 * @return Has-A relationship DOT format.
		 */
		String getHasRelationVizDescription() {
			return this.hasRelation.getOutput();
		}

		/**
		 * Returns the String of the Depends-On relationship of the class in DOT
		 * file format.
		 *
		 * @return Depends-On relationship DOT format.
		 */
		String getDependsRelationVizDescription() {
			return this.dependsOn.getOutput();
		}

		/**
		 * Returns the String of the SuperClass in DOT file format.
		 *
		 * @return SuperClass in DOT format.
		 */
		String getSuperClassVizDescription() {
			return extendsRel.parse(model, model.getSuperClass());
		}

		/**
		 * Returns the String of the Class (header, fields, methods) in DOT file
		 * format.
		 *
		 * @return Class in DOT format.
		 */
		String getClassVizDescription() {
			// Set Description block.
			StringBuilder sb = new StringBuilder();
			sb.append("\t");
			sb.append("\"").append(this.name).append("\"");
			sb.append(" [\n");
			// TODO: This may change with the configuration

			// Set the header.
			sb.append("\t\tlabel = \"");
			sb.append("{");
			sb.append(getHeader());
			sb.append(" | ");

			// Set the fields.
			// Check to avoid double lines if there are no fields.
			if (!getFields().isEmpty()) {
				sb.append(getFields());
				sb.append(" | ");
			}

			// Set the methods.
			if (!getMethods().isEmpty()) {
				sb.append(getMethods());
				sb.append("}\"\n\t];");
			}
			return sb.toString();
		}
	}

}