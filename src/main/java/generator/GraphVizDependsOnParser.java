package generator;

import java.util.Collection;

/**
 * A GraphVizParser for the model's depends on Relationship.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizDependsOnParser implements IGraphVizParser {
    private StringBuilder dependsOnVizDescription;

    public GraphVizDependsOnParser(Iterable<? extends IClassModel> dependencies, Collection<IModifier> filters, String className) {
        this.dependsOnVizDescription = new StringBuilder();
        generateDependsRelationVizDescription(dependencies, filters, className);
    }

    public String getOutput() {
        return this.dependsOnVizDescription.toString();
    }

    private void generateDependsRelationVizDescription(Iterable<? extends IClassModel> dependencies, Collection<IModifier> filters, String className) {
        GraphVizDependencyFormatter.setupDependencyVizDescription(this.dependsOnVizDescription, className);

        int dependencyLengthBefore = this.dependsOnVizDescription.length();

        dependencies.forEach((dependency) -> {
            if (!filters.contains(dependency.getModifier())) {
                this.dependsOnVizDescription.append("\"").append(dependency.getName()).append("\"");
                this.dependsOnVizDescription.append(", ");
            }
        });

        GraphVizDependencyFormatter.closeDependencyVizDescription(this.dependsOnVizDescription, dependencyLengthBefore);
    }
}
