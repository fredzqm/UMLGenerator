package adapter.classParser;

import analyzer.utility.IFieldModel;
import analyzer.utility.ITypeModel;
import utility.Modifier;

/**
 * A GraphVizParser for the Field class.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizFieldParser implements IParser<IFieldModel> {
    @Override
    public String parse(IFieldModel field, ClassParserConfiguration config) {
        IParser<ITypeModel> typeParser = config.getTypeParser();
        IParser<Modifier> modifierParser = config.getModifierParser();
        return String.format("%s %s : %s \\l", modifierParser.parse(field.getModifier(), config), field.getName(),
                typeParser.parse(field.getFieldType(), config));
    }
}