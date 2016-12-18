package generator.parser;

import generator.IClassModel;
import generator.IParser;
import utility.IFilter;
import utility.Modifier;

/**
 * A GraphVizParser for the model's SuperClass.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizSuperClassRelParser implements IParser<IClassModel> {
    private IFilter<Modifier> modifierFilter;

    public GraphVizSuperClassRelParser(IFilter<Modifier> filters2) {
        this.modifierFilter = filters2;
    }

    @Override
    public String parse(IClassModel thisClass) {
        IClassModel superClass = thisClass.getSuperClass();
        if (superClass == null || !modifierFilter.filter(superClass.getModifier())) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        GraphVizDependencyFormatter.setupDependencyVizDescription(sb, thisClass.getName());
        sb.append(String.format("\"%s\"};\n", superClass.getName()));

        return sb.toString();
    }

}