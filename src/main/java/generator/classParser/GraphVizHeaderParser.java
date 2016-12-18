package generator.classParser;

import generator.*;
import utility.ClassType;

public class GraphVizHeaderParser implements IParser<IClassModel> {

    private IParser<ClassType> classTypeParser;

    GraphVizHeaderParser(IParser<ClassType> classTypeParser) {
        this.classTypeParser = classTypeParser;
    }

    @Override
    public String parse(IClassModel data) {
        return classTypeParser.parse(data.getType()) + data.getName();
    }

}
