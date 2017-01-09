package analyzerClassParser;

import java.util.List;

import analyzer.IMethodModel;
import analyzer.ITypeModel;
import utility.Modifier;

/**
 * A GraphVizParser for the model's Methods.
 * <p>
 * Created by lamd on 12/14/2016.
 */
public class GraphVizMethodParser implements IParser<IMethodModel> {
    @Override
    public String parse(IMethodModel method, IClassParserConfiguration config) {
        IParser<Modifier> modifierParser = config.getModifierParser();
        IParser<ITypeModel> typeParser = config.getTypeParser();
        
        String modifier = modifierParser.parse(method.getModifier(), config);
        String name = method.getName();
        String returnType = typeParser.parse(method.getReturnType(), config);
        
        StringBuilder argumentList = new StringBuilder();
        List<? extends ITypeModel> args = method.getArguments();
        if (!args.isEmpty()) {
            argumentList.append(String.format("%s", typeParser.parse(args.get(0), config)));
            for (int i = 1; i < args.size(); i++) {
                argumentList.append(String.format(", %s", typeParser.parse(args.get(i), config)));
            }
        }
        return String.format("%s %s(%s) : %s \\l", modifier, name, argumentList, returnType);
    }
    
}