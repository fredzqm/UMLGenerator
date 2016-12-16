package generator;

import utility.IFilter;
import utility.Modifier;

/**
 * A GraphVizParser for the Field class.
 * <p>
 * Created by lamd on 12/14/2016.
 */
class GraphVizFieldParser implements IParser<IFieldModel> {
    private IFilter<Modifier> enumFilter;

    public GraphVizFieldParser(IFilter<Modifier> filters2) {
        this.enumFilter = filters2;
    }

    @Override
    public String parse(IFieldModel field) {
        StringBuilder classFields = new StringBuilder();
        if (enumFilter.filter(field.getModifier())) {
            classFields.append(String.format("%s %s : %s \\l", field.getModifier().getModifierSymbol(), field.getName(), field.getType().getName()));
        }
        return classFields.toString();
    }
}