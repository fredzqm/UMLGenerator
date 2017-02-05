package adapter.relationParser;

import analyzer.utility.IAnalyzer;
import analyzer.utility.ISystemModel;
import config.IConfiguration;

public class RelationParserAnalyzer implements IAnalyzer {
    @Override
    public ISystemModel analyze(ISystemModel sm, IConfiguration config) {
        return new MergeRelationSystemModel(new ParseRelationSystemModel(sm));
    }
}
