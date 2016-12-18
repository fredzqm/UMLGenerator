package generator;

import generator.classParser.IParser;
import utility.IFilter;
import utility.Modifier;

public abstract class AbstractClassParser implements IParser<IClassModel> {

	private IParser<IClassModel> header;

	private IFilter<IFieldModel> fieldFilters;
	private IParser<IFieldModel> fieldParser;

	private IFilter<IMethodModel> methodFilters;
	private IParser<IMethodModel> methodParser;

	public AbstractClassParser(IFilter<Modifier> filters, IFilter<IFieldModel> fieldFilters,
			IFilter<IMethodModel> methodFilters) {
		this.fieldFilters = fieldFilters;
		this.methodFilters = methodFilters;

		this.header = createHeaderParser();
		this.fieldParser = createFieldParser(filters);
		this.methodParser = createMethodParser(filters);
	}

	/**
	 * Returns the String of the Class (header, fields, methods) in DOT file
	 * format.
	 *
	 * @return Class in DOT format.
	 */
	@Override
	public String parse(IClassModel model) {
		StringBuilder sb = new StringBuilder();
		String name = model.getName();

		// Set the header.
		sb.append(header.parse(model));

		// Set the fields.
		Iterable<? extends IFieldModel> fields = model.getFields();
		if (fields.iterator().hasNext()) {
			sb.append(String.format(" | %s", fieldParser.parse(this.fieldFilters.filter(fields))));
		}

		// Set the methods.
		Iterable<? extends IMethodModel> methods = model.getMethods();
		if (methods.iterator().hasNext()) {
			sb.append(String.format(" | %s", methodParser.parse(this.methodFilters.filter(methods))));
		}

		// generate the full string with the label text generated above.
		return String.format("\t\"%s\" [\n\t\tlabel = \"{%s}\"\n\t]\n", name, sb.toString());
	}

	/**
	 * Returns a Method Parser.
	 *
	 * @param filters
	 *            Filters for Parser.
	 * @return Method Parser.
	 */
	public abstract IParser<IMethodModel> createMethodParser(IFilter<Modifier> filters);

	/**
	 * Returns a Field Parser.
	 *
	 * @param filters
	 *            Filters for Parser.
	 * @return Field Parser.
	 */
	public abstract IParser<IFieldModel> createFieldParser(IFilter<Modifier> filters);

	/**
	 * Returns a Header Parser.
	 *
	 * @return Header Parser.
	 */
	public abstract IParser<IClassModel> createHeaderParser();

}
