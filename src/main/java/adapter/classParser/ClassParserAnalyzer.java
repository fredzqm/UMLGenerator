package adapter.classParser;

import analyzer.utility.IAnalyzer;
import analyzer.utility.ISystemModel;
import config.IConfiguration;

/**
 * It uses {link GraphVizClassParser} to parse the label of all the classes in
 * system model
 *
 * @author zhang
 */
public class ClassParserAnalyzer implements IAnalyzer {
    @Override
    public ISystemModel analyze(ISystemModel systemModel, IConfiguration config) {
        ClassParserConfiguration classParserConfig = config.createConfiguration(ClassParserConfiguration.class);
        return new ParseClassSystemModel(systemModel, classParserConfig);
    }
}
