package analyzerClassParser;

import analyzer.IClassModel;
import analyzer.IFieldModel;
import analyzer.IMethodModel;
import utility.IFilter;
import utility.Modifier;

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
		Class<? extends IParser<IClassModel>> headerParserClass = config.getHeaderParser();
		try {
			IParser<IClassModel> header = headerParserClass.newInstance();
			sb.append(header.parse(model, config));
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		// Filter the fields
		Iterable<? extends IFieldModel> fields = model.getFields();
		IFilter<IFieldModel> fieldFilters = (f) -> modifierFilter.filter(f.getModifier());
		fields = fieldFilters.filter(fields);
		// Render the fields
		Class<? extends IParser<IFieldModel>> fieldParserClass = config.getFieldParser();
		try {
			IParser<IFieldModel> fieldParser = fieldParserClass.newInstance();
			if (fields.iterator().hasNext()) {
				sb.append(String.format(" | %s", fieldParser.parse(fields, config)));
			}
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		// Filter the methods
		Iterable<? extends IMethodModel> methods = model.getMethods();
		IFilter<IMethodModel> methodFilters = (m) -> modifierFilter.filter(m.getModifier());
		methods = methodFilters.filter(methods);
		// Render the methods
		Class<? extends IParser<IMethodModel>> methodParserClass = config.getMethodParser();
		try {
			IParser<IMethodModel> methodParser = methodParserClass.newInstance();
			if (methods.iterator().hasNext()) {
				sb.append(String.format(" | %s", methodParser.parse(methods, config)));
			}
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		// Generate the full string with the label text generated above.
		return sb.toString();
	}

}
