package generator;

/**
 * A GraphVizParser for the model's interface.
 *
 * Created by lamd on 12/14/2016.
 */
public class GraphVizInterfaceParser implements IGraphVizParser {
    private StringBuilder interfaceVizDescription;

    public GraphVizInterfaceParser(Iterable<? extends IClassModel> interfaces, String className) {
        this.interfaceVizDescription = new StringBuilder();
        generateInterfaceVizDescription(interfaces, className);
    }

    public String getOutput() {
        return this.interfaceVizDescription.toString();
    }

    private void generateInterfaceVizDescription(Iterable<? extends IClassModel> interfaces, String className) {
        GraphVizDependencyFormatter.setupDependencyVizDescription(this.interfaceVizDescription, className);
        int interfaceLengthBefore = this.interfaceVizDescription.length();

        interfaces.forEach((interfaceModel) -> {
            this.interfaceVizDescription.append("\"").append(interfaceModel.getName()).append("\"");
            this.interfaceVizDescription.append(", ");
        });

        // If it is empty close the braces without replacing characters.
        GraphVizDependencyFormatter.closeDependencyVizDescription(this.interfaceVizDescription, interfaceLengthBefore);
    }
}
