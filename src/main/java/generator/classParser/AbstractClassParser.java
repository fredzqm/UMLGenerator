package generator.classParser;

import generator.IGeneratorConfiguration;
import utility.IFilter;

public abstract class AbstractClassParser implements IParser<IClassModel> {
    private final IParser<IClassModel> header;
    private final IFilter<IFieldModel> fieldFilters;
    private final IParser<IFieldModel> fieldParser;
    private final IFilter<IMethodModel> methodFilters;
    private final IParser<IMethodModel> methodParser;

    AbstractClassParser(IGeneratorConfiguration config) {
        this.fieldFilters = createFieldFilter(config);
        this.methodFilters = createFieldMethodFilter(config);
        this.header = createHeaderParser(config);
        this.fieldParser = createFieldParser(config);
        this.methodParser = createMethodParser(config);
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
        StringBuilder fieldBuilder = new StringBuilder();
        if (fields.iterator().hasNext()) {
            fieldBuilder.append(fieldParser.parse(this.fieldFilters.filter(fields)));
        }

        // Only append to main StringBuilder if it is non-empty.
        if (fieldBuilder.length() > 0) {
            sb.append(String.format(" | %s", fieldBuilder.toString()));
        }

        // Set the methods.
        Iterable<? extends IMethodModel> methods = model.getMethods();
        if (methods.iterator().hasNext()) {
            sb.append(String.format(" | %s", methodParser.parse(this.methodFilters.filter(methods))));
        }

        // Generate the full string with the label text generated above.
        return String.format("\t\"%s\" [\n\t\tlabel = \"{%s}\"\n\t];\n", name, sb.toString());
    }

    /**
     * Returns a Method Filter.
     *
     * @param config Generator Configuration.
     * @return Filter for Methods.
     */
    public abstract IFilter<IMethodModel> createFieldMethodFilter(IGeneratorConfiguration config);

    /**
     * Returns a Method Parser.
     *
     * @param config Generator Configuration.
     * @return Method Parser.
     */
    public abstract IParser<IMethodModel> createMethodParser(IGeneratorConfiguration config);

    /**
     * Returns a Field Filter.
     *
     * @param config Generator Configuration.
     * @return Filter for the Fields.
     */
    public abstract IFilter<IFieldModel> createFieldFilter(IGeneratorConfiguration config);

    /**
     * Returns a Field Parser.
     *
     * @param config
     * @return Field Parser.
     */
    public abstract IParser<IFieldModel> createFieldParser(IGeneratorConfiguration config);

    /**
     * Returns a Header Parser.
     *
     * @param config
     * @return Header Parser.
     */
    public abstract IParser<IClassModel> createHeaderParser(IGeneratorConfiguration config);

}
