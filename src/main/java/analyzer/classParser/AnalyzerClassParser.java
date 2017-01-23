package analyzer.classParser;

import analyzer.utility.IAnalyzer;
import analyzer.utility.ISystemModel;
import config.ClassParserConfiguration;
import config.IConfiguration;

/**
 * It uses {link GraphVizClassParser} to parse the label of all the classes in
 * system model
 *
 * @author zhang
 */
public class AnalyzerClassParser implements IAnalyzer {
    @Override
    public ISystemModel analyze(ISystemModel systemModel, IConfiguration config) {
        IClassParserConfiguration classParserConfig = config.createConfiguration(ClassParserConfiguration.class);
        return new ParseClassSystemModel(systemModel, classParserConfig);
    }
}
