package generator;

import java.util.Collection;

/**
 * A GraphVizParser for the model's HasRelations.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizHasParser implements IGraphVizParser {
    private StringBuilder hasRelationVizDescription;

    public GraphVizHasParser(Iterable<? extends IClassModel> hasRelations, Collection<IModifier> filters, String className) {
        this.hasRelationVizDescription = new StringBuilder();
        generateHasRelationVizDescription(hasRelations, filters, className);
    }

    @Override
    public String getOutput() {
        return this.hasRelationVizDescription.toString();
    }

    private void generateHasRelationVizDescription(Iterable<? extends IClassModel> hasRelations, Collection<IModifier> filters, String name) {
        GraphVizDependencyFormatter.setupDependencyVizDescription(this.hasRelationVizDescription, name);
        int hasALengthBefore = this.hasRelationVizDescription.length();

        hasRelations.forEach((has) -> {
            if (!filters.contains(has.getModifier())) {
                this.hasRelationVizDescription.append("\"").append(has.getName()).append("\"");
                this.hasRelationVizDescription.append(", ");
            }
        });

        GraphVizDependencyFormatter.closeDependencyVizDescription(this.hasRelationVizDescription, hasALengthBefore);
    }
}
