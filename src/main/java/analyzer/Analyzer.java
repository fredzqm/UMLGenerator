package analyzer;

import generator.ISystemModel;

public class Analyzer implements IAnalyzer {

    @Override
    public ISystemModel analyze(ISystemModel sm) {
        return new AnalyzedSystemModel(sm);
    }

}
