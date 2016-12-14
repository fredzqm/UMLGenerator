package generator;

import java.util.Collection;

/**
 * A GraphVizParser for the model's SuperClass.
 *
 * Created by lamd on 12/14/2016.
 */
public class GraphVizSuperClassParser implements IGraphVizParser {
    private StringBuilder superClassVizDescription;

    public GraphVizSuperClassParser(IClassModel superClass, Collection<IModifier> filters, String className) {
        this.superClassVizDescription = new StringBuilder();
        generateSuperClassVizDescription(superClass, filters, className);
    }

    @Override
    public String getOutput() {
        return this.superClassVizDescription.toString();
    }

    private void generateSuperClassVizDescription(IClassModel superClass, Collection<IModifier> filters, String className) {
        // Ensure the superclass is not null and the filter does not exclude it.
        // Uses shortcircuiting.
        if (superClass == null || filters.contains(superClass.getModifier())) {
            return;
        }

        GraphVizDependencyFormatter.setupDependencyVizDescription(this.superClassVizDescription, className);

        this.superClassVizDescription.append("\"").append(superClass.getName()).append("\"");
        this.superClassVizDescription.append("};\n");
    }
}
