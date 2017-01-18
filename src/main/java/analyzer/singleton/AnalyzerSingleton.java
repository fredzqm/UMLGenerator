package analyzer.singleton;

import analyzer.utility.IAnalyzer;
import analyzer.utility.IAnalyzerConfiguration;
import analyzer.utility.ISystemModel;

public class AnalyzerSingleton implements IAnalyzer {

    @Override
    public ISystemModel analyze(ISystemModel systemModel, IAnalyzerConfiguration config) {
        return new SingletonSystemModel(systemModel);
    }

}
