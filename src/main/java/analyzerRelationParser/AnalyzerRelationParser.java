package analyzerRelationParser;

import analyzer.ISystemModel;
import analyzer.IAnalyzer;
import analyzer.IAnalyzerConfiguraton;

public class AnalyzerRelationParser implements IAnalyzer {

	@Override
	public ISystemModel analyze(ISystemModel sm, IAnalyzerConfiguraton config) {
        return new MergeRelationSystemModel(new ParseRelationSystemModel(sm));
	}

}
