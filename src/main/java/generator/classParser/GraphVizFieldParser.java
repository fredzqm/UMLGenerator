package generator.classParser;

import generator.*;
import utility.IFilter;
import utility.Modifier;

/**
 * A GraphVizParser for the Field class.
 * <p>
 * Created by lamd on 12/14/2016.
 */
class GraphVizFieldParser implements IParser<IFieldModel> {
    private IFilter<Modifier> enumFilter;

    GraphVizFieldParser(IFilter<Modifier> filters) {
        this.enumFilter = filters;
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