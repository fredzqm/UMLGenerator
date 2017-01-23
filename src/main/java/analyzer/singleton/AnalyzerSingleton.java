package analyzer.singleton;

import analyzer.utility.IAnalyzer;
import analyzer.utility.ISystemModel;
import config.IConfiguration;

public class AnalyzerSingleton implements IAnalyzer {

    @Override
    public ISystemModel analyze(ISystemModel systemModel, IConfiguration config) {
        return new SingletonSystemModel(systemModel);
    }

}
