package adapter.classParser;

import analyzer.utility.IMethodModel;
import analyzer.utility.ISystemModel;
import analyzer.utility.ITypeModel;
import utility.Modifier;

import java.util.List;

/**
 * A GraphVizParser for the model's Methods.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizMethodParser implements IParser<IMethodModel> {
    @Override
    public String parse(IMethodModel method, ISystemModel systemModel, ClassParserConfiguration config) {
        IParser<Modifier> modifierParser = config.getModifierParser();
        IParser<ITypeModel> typeParser = config.getTypeParser();

        String modifier = modifierParser.parse(method.getModifier(), systemModel, config);
        String name = method.getName();
        String returnType = typeParser.parse(method.getReturnType(), systemModel, config);

        StringBuilder argumentList = new StringBuilder();
        List<? extends ITypeModel> args = method.getArguments();
        if (!args.isEmpty()) {
            argumentList.append(String.format("%s", typeParser.parse(args.get(0), systemModel, config)));
            for (int i = 1; i < args.size(); i++) {
                argumentList.append(String.format(", %s", typeParser.parse(args.get(i), systemModel, config)));
            }
        }
        return String.format("%s %s(%s) : %s \\l", modifier, name, argumentList, returnType);
    }

}