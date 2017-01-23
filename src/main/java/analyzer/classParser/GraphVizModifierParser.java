package analyzer.classParser;

import utility.Modifier;

public class GraphVizModifierParser implements IParser<Modifier> {

    @Override
    public String parse(Modifier modifier, ClassParserConfiguration config) {
        switch (modifier) {
            case DEFAULT:
                return "\\ \\ ";
            case PRIVATE:
                return "-";
            case PROTECTED:
                return "#";
            case PUBLIC:
                return "+";
            default:
                throw new RuntimeException();
        }
    }

}
