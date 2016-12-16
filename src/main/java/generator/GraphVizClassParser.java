package generator;

import java.util.Collection;

import utility.ClassType;

/**
 * Representing a single class in the DOT language.
 */
public class GraphVizClassParser implements IParser<IClassModel> {
    private IParser<ClassType> classTypeParser;
    private IParser<IFieldModel> fieldParser;
    private IParser<IMethodModel> methodParser;

    GraphVizClassParser(Collection<IModifier> filters) {
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
        StringBuilder sb = new StringBuilder();
        String name = model.getName();
        // TODO: for interfaces here.
//        if (name.equals("java.io.Serializable")) {
//            System.out.println("Serializable");
//        }

        ClassType classType = model.getType();

        // Set Description block.
        sb.append(String.format("\t\"%s\" [\n", name));

        // Set the header.
        sb.append(String.format("\t\tlabel = \"{%s%s", classTypeParser.parse(classType), model.getName()));

        // Set the fields.
        // Check to avoid double lines if there are no fields.
        Iterable<? extends IFieldModel> fields = model.getFields();
        if (fields.iterator().hasNext()) {
            sb.append(String.format(" | %s", fieldParser.parse(fields)));
        }

        // Set the methods.
        Iterable<? extends IMethodModel> methods = model.getMethods();
        if (methods.iterator().hasNext()) {
            sb.append(String.format(" | %s", methodParser.parse(methods)));
        }

        // End the Class Block.
        sb.append("}\"\n\t]\n");

        return sb.toString();
    }

}
