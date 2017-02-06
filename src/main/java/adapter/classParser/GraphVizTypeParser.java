package adapter.classParser;

import analyzer.utility.ISystemModel;
import analyzer.utility.ITypeModel;

public class GraphVizTypeParser implements IParser<ITypeModel> {

    @Override
    public String parse(ITypeModel type, ISystemModel systemModel, ClassParserConfiguration config) {
        if (type.isWildCharacter()) {
            return " ? ";
        } else if (type.getLowerBound() != null) {
            return String.format(" ? extends %s", parse(type.getLowerBound(), systemModel, config));
        } else if (type.getUpperBound() != null) {
            return String.format(" ? super %s", parse(type.getUpperBound(), systemModel, config));
        } else if (type.getGenericArgNumber() == 0) {
            return type.getName();
        } else {
            // generic parameter
            StringBuilder sb = new StringBuilder();
            sb.append(parse(type.getGenericArg(0), systemModel, config));
            for (int i = 1; i < type.getGenericArgNumber(); i++) {
                sb.append("," + parse(type.getGenericArg(i), systemModel, config));
            }
            return String.format("%s\\<%s\\>", type.getName(), sb);
        }
    }

}
