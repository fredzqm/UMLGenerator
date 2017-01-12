package analyzerClassParser;

import analyzer.ITypeModel;

public class GraphVizTypeParser implements IParser<ITypeModel> {

    @Override
    public String parse(ITypeModel type, IClassParserConfiguration config) {
        if (type.isWildCharacter()) {
            return "*";
        } else if (type.getLowerBound() != null) {
            return String.format("extends %s", parse(type.getLowerBound(), config));
        } else if (type.getUpperBound() != null) {
            return String.format("super %s", parse(type.getUpperBound(), config));
        } else if (type.getGenericArgNumber() == 0) {
            return type.getName();
        } else {
            // generic parameter
            StringBuilder sb = new StringBuilder();
            sb.append(type.getGenericArg(0));
            for (int i = 1; i < type.getGenericArgNumber(); i++) {
                sb.append("," + parse(type.getGenericArg(i), config));
            }
            return String.format("%s\\<%s\\>", type.getName(), sb);
        }
    }

}
