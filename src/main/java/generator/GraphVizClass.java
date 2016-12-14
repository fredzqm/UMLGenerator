package generator;

import java.util.Collection;

/**
 * Representing a single class in the DOT language.
 */
public class GraphVizClass implements IParser<IClassModel> {
	private String name;
	private IClassModel model;

	private IParser<IClassModel.IClassType> classTypeParser;
	private IParser<IFieldModel> fieldParser;
	private IParser<IMethodModel> methodParser;
	private IRelationParser extendsRel, implementsRel;

	// TODO:
	private IGraphVizParser hasRelation, dependsOn;

	GraphVizClass(IClassModel model, Collection<IModifier> filters) {
		this.name = model.getName();
		this.model = model;

		// this.header = new GraphVizHeaderParser(model.getType(), this.name);
		classTypeParser = new GraphVizClassTypeParser();

		this.fieldParser = new GraphVizFieldParser(filters);
		this.methodParser = new GraphVizMethodParser(filters);

		// class relationship they should not be here
		// TODO: refactor relationship to generator
		this.extendsRel = new GraphizExtendsRelParser(filters);
		this.implementsRel = new GraphVizInterfaceParser();
		// TODO: has not refactor yet
		this.hasRelation = new GraphVizHasParser(model.getHasRelation(), filters, this.name);
		this.dependsOn = new GraphVizDependsOnParser(model.getDependsRelation(), filters, this.name);
	}

	@Override
	public String parse(IClassModel data) {

		return null;
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
		sb.append(classTypeParser.parse(model.getType()));
		sb.append(model.getName());
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