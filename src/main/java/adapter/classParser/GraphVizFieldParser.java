package adapter.classParser;

import analyzer.utility.IFieldModel;
import analyzer.utility.ISystemModel;
import analyzer.utility.ITypeModel;
import utility.Modifier;

/**
 * A GraphVizParser for the Field class.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizFieldParser implements IParser<IFieldModel> {
    @Override
    public String parse(IFieldModel field, ISystemModel systemModel, ClassParserConfiguration config) {
        IParser<ITypeModel> typeParser = config.getTypeParser();
        IParser<Modifier> modifierParser = config.getModifierParser();
        return String.format("%s %s : %s \\l", modifierParser.parse(field.getModifier(), systemModel, config), field.getName(),
                typeParser.parse(field.getFieldType(), systemModel, config));
    }
}