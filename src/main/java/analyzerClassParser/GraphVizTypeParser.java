package analyzerClassParser;

import analyzer.ITypeModel;

public class GraphVizTypeParser implements IParser<ITypeModel> {

    @Override
    public String parse(ITypeModel type, IClassParserConfiguration config) {
        if (type.getGenericArgNumber() == 0)
            return type.getName();
        StringBuilder sb = new StringBuilder();
        sb.append(type.getGenericArg(0));
        for (int i = 1; i < type.getGenericArgNumber(); i++) {
            sb.append("," + parse(type.getGenericArg(i), config));
        }
        return String.format("%s\\<%s\\>", type.getName(), sb);
    }

}
