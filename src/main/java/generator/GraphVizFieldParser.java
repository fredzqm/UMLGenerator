package generator;

import java.util.Collection;

/**
 * A GraphVizParser for the Field class.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizFieldParser implements IParser<IFieldModel> {
    private Collection<IModifier> filters;

    public GraphVizFieldParser(Collection<IModifier> filters) {
        this.filters = filters;
    }

    @Override
    public String parse(IFieldModel field) {
        StringBuilder classFields = new StringBuilder();
        if (!filters.contains(field.getModifier())) {
            classFields.append(String.format("%s %s : %s \\l", field.getModifier().getModifierSymbol(), field.getName(), field.getType().getName()));
        }
        return classFields.toString();
    }
}