package generator;

import java.util.Collection;

import utility.IFilter;
import utility.Modifier;

/**
 * A GraphVizParser for the model's SuperClass.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizSuperClassRelParser implements IParser<IClassModel> {
    private IFilter<Modifier> modifierFilter;

    GraphVizSuperClassRelParser(IFilter<Modifier> filters2) {
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