package analyzerClassParser;

import analyzer.ISystemModel;
import analyzer.IAnalyzer;
import analyzer.IAnalyzerConfiguraton;
import analyzer.IClassModel;

/**
 * It uses {link GraphVizClassParser} to parse the label of all the classes in
 * system model
 * 
 * @author zhang
 *
 */
public class AnalyzerClassParser implements IAnalyzer {

	@Override
	public ISystemModel analyze(ISystemModel systemModel, IAnalyzerConfiguraton config) {
		Object c = config.getConfigurationFor(AnalyzerClassParser.class);
		if (!(c instanceof IClassParserConfiguration))
			throw new RuntimeException(c + " is not a IClassParserConfiguration");

		IParser<IClassModel> classParser = new GraphVizClassParser();

		return new ParseClassSystemModel(systemModel, classParser, (IClassParserConfiguration) c);
	}

}
