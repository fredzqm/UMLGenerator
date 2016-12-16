package generator;

import java.util.Collection;

import utility.Modifier;

/**
 * A GraphVizParser for the model's depends on Relationship.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizDependsOnRelParser implements IParser<IClassModel> {
    private Collection<Modifier> filters;

    GraphVizDependsOnRelParser(Collection<Modifier> filters) {
        this.filters = filters;
    }

    @Override
    public String parse(IClassModel thisClass) {
        Iterable<? extends IClassModel> otherClassList = thisClass.getDependsRelation();

        StringBuilder sb = new StringBuilder();
        GraphVizDependencyFormatter.setupDependencyVizDescription(sb, thisClass.getName());
        int dependsOnLengthBefore = sb.length();

        otherClassList.forEach((has) -> {
            if (!filters.contains(has.getModifier())) {
                sb.append(String.format("\"%s\" ", has.getName()));
            }
        });

        GraphVizDependencyFormatter.closeDependencyVizDescription(sb, dependsOnLengthBefore);
        return sb.toString();
    }

}