package generator;

import java.util.Collection;

/**
 * A GraphVizParser for the model's SuperClass.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizSuperClassRelParser implements IParser<IClassModel> {
    private Collection<IModifier> filters;

    GraphVizSuperClassRelParser(Collection<IModifier> filters) {
        this.filters = filters;
    }

    @Override
    public String parse(IClassModel thisClass) {
        IClassModel superClass = thisClass.getSuperClass();
        if (superClass == null || filters.contains(superClass.getModifier())) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        GraphVizDependencyFormatter.setupDependencyVizDescription(sb, thisClass.getName());
        sb.append(String.format("\"%s\"};\n\n\t", superClass.getName()));

        return sb.toString();
    }

}
