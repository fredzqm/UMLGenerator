package generator.parser;

import generator.IClassModel;
import generator.IFieldModel;
import generator.IMethodModel;
import generator.IParser;
import utility.IFilter;
import utility.Modifier;

/**
 * Representing a single class in the DOT language.
 */
public class GraphVizClassParser implements IParser<IClassModel> {
    private final IClassElementParserFactory factory; // FIXME: This may not need to be a field variable.
    private IParser<IClassModel> header;

    private IFilter<IFieldModel> fieldFilters;
    private IParser<IFieldModel> fieldParser;

    private IFilter<IMethodModel> methodFilters;
    private IParser<IMethodModel> methodParser;

    public GraphVizClassParser(IFilter<Modifier> filters, IFilter<IFieldModel> fieldFilters, IFilter<IMethodModel> methodFilters) {
        this.fieldFilters = fieldFilters;
        this.methodFilters = methodFilters;

        this.factory = new GraphVizClassElementParserFactory();

        this.header = this.factory.createHeaderParser();
        this.fieldParser = this.factory.createFieldParser(filters);
        this.methodParser = this.factory.createMethodParser(filters);
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

}
