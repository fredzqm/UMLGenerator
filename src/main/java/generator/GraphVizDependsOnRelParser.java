package generator;

import java.util.Collection;

import utility.IFilter;
import utility.Modifier;

/**
 * A GraphVizParser for the model's depends on Relationship.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizDependsOnRelParser implements IParser<IClassModel> {
    private IFilter<Modifier> modifierFilter;

    GraphVizDependsOnRelParser(IFilter<Modifier> filter) {
        this.modifierFilter = filter;
    }

    @Override
    public String parse(IClassModel thisClass) {
        Iterable<? extends IClassModel> otherClassList = thisClass.getDependsRelation();

        StringBuilder sb = new StringBuilder();
        GraphVizDependencyFormatter.setupDependencyVizDescription(sb, thisClass.getName());
        int dependsOnLengthBefore = sb.length();

        otherClassList.forEach((has) -> {
            if (modifierFilter.filter(has.getModifier())) {
                sb.append(String.format("\"%s\", ", has.getName()));
            }
        });

        GraphVizDependencyFormatter.closeDependencyVizDescription(sb, dependsOnLengthBefore);
        return sb.toString();
    }

}