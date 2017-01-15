package analyzer.relationParser;

import analyzer.utility.IAnalyzer;
import analyzer.utility.IAnalyzerConfiguration;
import analyzer.utility.ISystemModel;

public class AnalyzerRelationParser implements IAnalyzer {
    @Override
    public ISystemModel analyze(ISystemModel sm, IAnalyzerConfiguration config) {
        return new MergeRelationSystemModel(new ParseRelationSystemModel(sm));
    }
}
