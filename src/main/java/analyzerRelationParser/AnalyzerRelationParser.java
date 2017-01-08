package analyzerRelationParser;

import analyzer.IAnalyzer;
import analyzer.IAnalyzerConfiguration;
import analyzer.ISystemModel;

public class AnalyzerRelationParser implements IAnalyzer {
    @Override
    public ISystemModel analyze(ISystemModel sm, IAnalyzerConfiguration config) {
        return new MergeRelationSystemModel(new ParseRelationSystemModel(sm));
    }
}
