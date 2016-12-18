package generator.parser;

import generator.IClassModel;
import generator.IParser;
import utility.IFilter;
import utility.Modifier;

/**
 * A GraphVizParser for the model's HasRelations.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizHasRelParser implements IParser<IClassModel> {
    private IFilter<Modifier> modifierFilter;

    public GraphVizHasRelParser(IFilter<Modifier> filter) {
        this.modifierFilter = filter;
    }

    @Override
    public String parse(IClassModel thisClass) {
        Iterable<? extends IClassModel> otherClassList = thisClass.getHasRelation();

        StringBuilder sb = new StringBuilder();
        GraphVizDependencyFormatter.setupDependencyVizDescription(sb, thisClass.getName());
        int hasALengthBefore = sb.length();

        otherClassList.forEach((has) -> {
            if (modifierFilter.filter(has.getModifier())) {
                sb.append(String.format("\"%s\" ", has.getName()));
            }
        });

        GraphVizDependencyFormatter.closeDependencyVizDescription(sb, hasALengthBefore);

        return sb.toString();
    }

}