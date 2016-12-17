package generator.parser;

import generator.*;
import utility.IFilter;
import utility.Modifier;

/**
 * Representing a single class in the DOT language.
 */
public class GraphVizClassParser implements IParser<IClassModel> {
    private IParser<IClassModel> header;

    private IFilter<IFieldModel> fieldFilter;
    private IParser<IFieldModel> fieldParser;

    private IFilter<IMethodModel> methodFilter;
    private IParser<IMethodModel> methodParser;

    public GraphVizClassParser(IFilter<Modifier> filters, IFilter<IFieldModel> fieldFilter, IFilter<IMethodModel> methodFilter) {
        this.fieldFilter = fieldFilter;
        this.methodFilter = methodFilter;
        this.header = new GraphVizHeaderParser(new GraphVizClassTypeParser());
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
        StringBuilder sb = new StringBuilder();
        String name = model.getName();

        // Set the header.
        sb.append(header.parse(model));

        // Set the fields.
        Iterable<? extends IFieldModel> fields = model.getFields();
        if (fields.iterator().hasNext()) {
            sb.append(String.format(" | %s", fieldParser.parse(fieldFilter.filter(fields))));
        }

        // Set the methods.
        Iterable<? extends IMethodModel> methods = model.getMethods();
        if (methods.iterator().hasNext()) {
            sb.append(String.format(" | %s", methodParser.parse(methodFilter.filter(methods))));
        }

        // generate the full string with the label text generated above.
        return String.format("\t\"%s\" [\n\t\tlabel = \"{%s}\"\n\t]\n", name, sb.toString());
    }

}
