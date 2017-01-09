package analyzerClassParser;

import analyzer.ITypeModel;

public class GraphVizTypeParser implements IParser<ITypeModel> {
    
    @Override
    public String parse(ITypeModel type, IClassParserConfiguration config) {
        if (type.getGenericArgNumber() == 0)
            return type.getName();
        StringBuilder sb = new StringBuilder();
        sb.append(type.getGenericArg(0));
        for (int i = 0; i < type.getGenericArgNumber(); i++) {
            sb.append("," + type.getGenericArg(i));
        }
        return String.format("%s\\<%s\\>", type.getName(), sb);
    }
    
}
