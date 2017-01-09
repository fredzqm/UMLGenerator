package analyzerClassParser;

import analyzer.IClassModel;
import analyzer.IFieldModel;
import analyzer.IMethodModel;
import utility.IFilter;
import utility.Modifier;

/**
 * Representing a single class in the DOT language.
 */
public class GraphVizClassParser implements IParser<IClassModel> {

	/**
	 * Returns the String of the Class (header, fields, methods) in DOT file
	 * format.
	 *
	 * @return Class in DOT format.
	 */
	@Override
	public String parse(IClassModel model, IClassParserConfiguration config) {
        StringBuilder sb = new StringBuilder();
		IFilter<Modifier> modifierFilter = config.getModifierFilters();

		// Set the header.
		IParser<IClassModel> header = config.getHeaderParser();
		sb.append(header.parse(model, config));

		// Filter the fields
		Iterable<? extends IFieldModel> fields = model.getFields();
		IFilter<IFieldModel> fieldFilters = (f) -> modifierFilter.filter(f.getModifier());
		fields = fieldFilters.filter(fields);
		// Render the fields
		IParser<IFieldModel> fieldParser = config.getFieldParser();
		if (fields.iterator().hasNext()) {
			sb.append(String.format(" | %s", fieldParser.parse(fields, config)));
		}

		// Filter the methods
		Iterable<? extends IMethodModel> methods = model.getMethods();
		IFilter<IMethodModel> methodFilters = (m) -> modifierFilter.filter(m.getModifier());
		methods = methodFilters.filter(methods);
		// Render the methods
		IParser<IMethodModel> methodParser = config.getMethodParser();
		if (methods.iterator().hasNext()) {
			sb.append(String.format(" | %s", methodParser.parse(methods, config)));
		}

		// Generate the full string with the label text generated above.
		return sb.toString();
	}

}
