package adapter.classParser;

import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;

public class GraphVizHeaderParser implements IParser<IClassModel> {
    @Override
    public String parse(IClassModel classModel, ISystemModel systemModel, ClassParserConfiguration config) {
        StringBuilder sb = new StringBuilder();
        switch (classModel.getType()) {
            case INTERFACE:
                addHeader(sb, "Interface");
                break;
            case CONCRETE:
                break;
            case ABSTRACT:
                addHeader(sb, "Abstract");
                break;
            case ENUM:
                addHeader(sb, "Enumeration");
                break;
        }
        for (String sterotype : systemModel.getStereoTypes(classModel)) {
            addHeader(sb, sterotype);
        }
        sb.append(classModel.getName());
        return sb.toString();
    }

    private StringBuilder addHeader(StringBuilder sb, String sterotype) {
        return sb.append(String.format("\\<\\<%s\\>\\>\\n", sterotype));
    }
}