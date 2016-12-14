package generator;

/**
 * A GraphVizClassParser for all model's header, fields, and methods.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizClassParser implements IGraphVizParser {
    private StringBuilder classVizDescription;

    public GraphVizClassParser(String className, String header, String fields, String methods) {
        this.classVizDescription = new StringBuilder();
        generateClassVizDescription(className, header, fields, methods);
    }

    @Override
    public String getOutput() {
        return this.classVizDescription.toString();
    }

    private void generateClassVizDescription(String className, String header, String fields, String methods) {
        // Set Description block.
        this.classVizDescription.append("\t");
        this.classVizDescription.append("\"").append(className).append("\"");
        this.classVizDescription.append(" [\n");
        // TODO: This may change with the configuration

        // Set the header.
        this.classVizDescription.append("\t\tlabel = \"");
        this.classVizDescription.append("{");
        this.classVizDescription.append(header);
        this.classVizDescription.append(" | ");

        // Set the fields.
        // Check to avoid double lines if there are no fields.
        if (fields.length() != 0) {
            this.classVizDescription.append(fields);
            this.classVizDescription.append(" | ");
        }

        // Set the methods.
        this.classVizDescription.append(methods);
        this.classVizDescription.append("}\"\n\t];");
    }
}
