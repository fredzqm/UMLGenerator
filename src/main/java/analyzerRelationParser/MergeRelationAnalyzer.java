package analyzerRelationParser;

import analyzer.IASystemModel;
import analyzer.IAnalyzer;
import analyzer.IAnalyzerConfiguraton;

public class MergeRelationAnalyzer implements IAnalyzer {

	@Override
	public IASystemModel analyze(IASystemModel sm, IAnalyzerConfiguraton config) {
        return new MergeRelationSystemModel(sm);
	}

}
