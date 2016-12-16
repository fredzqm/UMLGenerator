package generator;

import java.util.Collection;

import utility.Modifier;

/**
 * A GraphVizParser for the model's SuperClass.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizSuperClassRelParser implements IParser<IClassModel> {
    private Collection<Modifier> filters;

    GraphVizSuperClassRelParser(Collection<Modifier> filters) {
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
        sb.append(String.format("\"%s\"};\n", superClass.getName()));

        return sb.toString();
    }

}