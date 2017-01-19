package analyzer.classParser;

import analyzer.utility.IAnalyzer;
import analyzer.utility.ISystemModel;

/**
 * It uses {link GraphVizClassParser} to parse the label of all the classes in
 * system model
 *
 * @author zhang
 */
public class AnalyzerClassParser implements IAnalyzer {
    @Override
    public ISystemModel analyze(ISystemModel systemModel, Object config) {
        if (!(config instanceof IClassParserConfiguration)) {
            throw new RuntimeException(config + " is not a IClassParserConfiguration");
        }
        return new ParseClassSystemModel(systemModel, (IClassParserConfiguration) config);
    }
}
