package analyzer.singleton;

import analyzer.utility.IAnalyzer;
import analyzer.utility.ISystemModel;

public class AnalyzerSingleton implements IAnalyzer {

    @Override
    public ISystemModel analyze(ISystemModel systemModel, Object config) {
        return new SingletonSystemModel(systemModel);
    }

}
