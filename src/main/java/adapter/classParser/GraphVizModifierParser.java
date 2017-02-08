package adapter.classParser;

import analyzer.utility.ISystemModel;
import utility.Modifier;

public class GraphVizModifierParser implements IParser<Modifier> {

    @Override
    public String parse(Modifier modifier, ISystemModel systemModel, ClassParserConfiguration config) {
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
