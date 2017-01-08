package analyzerClassParser;

import analyzer.IFieldModel;

/**
 * A GraphVizParser for the Field class.
 * <p>
 * Created by lamd on 12/14/2016.
 */
class GraphVizFieldParser implements IParser<IFieldModel> {
    @Override
    public String parse(IFieldModel field) {
        return String.format("%s %s : %s \\l", field.getModifier().getModifierSymbol(), field.getName(),
                field.getTypeName());
    }
}