package analyzer;

public class Analyzer implements IAnalyzer {

    @Override
    public IASystemModel analyze(IASystemModel sm) {
        return new AnalyzedSystemModel(sm);
    }

}
