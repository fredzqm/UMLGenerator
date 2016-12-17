package generator.parser;

import generator.IParser;
import utility.ClassType;

class GraphVizClassTypeParser implements IParser<ClassType> {

    @Override
    public String parse(ClassType classType) {
        switch (classType) {
            case INTERFACE:
                return "\\<\\<Interface\\>\\>\\n";
            case CONCRETE:
                return "";
            case ABSTRACT:
                return "\\<\\<Abstract\\>\\>\\n";
            case ENUM:
                return "\\<\\<Enumeration\\>\\>\\n";
            default:
                throw new RuntimeException("We are missing classType enum " + classType);
        }
    }

}