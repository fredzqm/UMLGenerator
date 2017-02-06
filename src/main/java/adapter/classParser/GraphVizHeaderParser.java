package adapter.classParser;

import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;

public class GraphVizHeaderParser implements IParser<IClassModel> {
    @Override
    public String parse(IClassModel classModel, ISystemModel systemModel, ClassParserConfiguration config) {
        StringBuilder headerBuilder = new StringBuilder();
        switch (classModel.getType()) {
            case INTERFACE:
                addHeader(headerBuilder, "Interface");
                break;
            case CONCRETE:
                break;
            case ABSTRACT:
                addHeader(headerBuilder, "Abstract");
                break;
            case ENUM:
                addHeader(headerBuilder, "Enumeration");
                break;
        }
        for (String sterotype : systemModel.getStereoTypes(classModel)) {
            addHeader(headerBuilder, sterotype);
        }
        headerBuilder.append(classModel.getName());
        return headerBuilder.toString();
    }

    private StringBuilder addHeader(StringBuilder sb, String sterotype) {
        return sb.append(String.format("\\<\\<%s\\>\\>\\n", sterotype));
    }
}