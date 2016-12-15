package generator;

import java.util.Collection;

/**
 * Representing a single class in the DOT language.
 */
public class GraphVizClassParser implements IParser<IClassModel> {
	private IParser<IClassModel.IClassType> classTypeParser;
	private IParser<IFieldModel> fieldParser;
	private IParser<IMethodModel> methodParser;

	public GraphVizClassParser(Collection<IModifier> filters) {
		// this.header = new GraphVizHeaderParser(model.getType(), this.name);
		this.classTypeParser = new GraphVizClassTypeParser();
		this.fieldParser = new GraphVizFieldParser(filters);
		this.methodParser = new GraphVizMethodParser(filters);
	}

	/**
	 * Returns the String of the Class (header, fields, methods) in DOT file
	 * format.
	 *
	 * @return Class in DOT format.
	 */
	@Override
	public String parse(IClassModel model) {
		// Set Description block.
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append("\"").append(model.getName()).append("\"");
		sb.append(" [\n");

		// Set the header.
		sb.append("\t\tlabel = \"");
		sb.append("{");
		sb.append(classTypeParser.parse(model.getType()));
		sb.append(model.getName());
		sb.append(" | ");

		// Set the fields.
		// Check to avoid double lines if there are no fields.
		if (model.getFields().iterator().hasNext()) {
			sb.append(fieldParser.parse(model.getFields()));
			sb.append(" | ");
		}

		// Set the methods.
		if (model.getMethods().iterator().hasNext()) {
			sb.append(methodParser.parse(model.getMethods()));
			sb.append("}\"\n\t];");
		}
		sb.append('\n');
		return sb.toString();
	}

}