package analyzer.singleton;

import analyzer.utility.IAnalyzer;
import analyzer.utility.ISystemModel;
import config.IConfiguration;

public class SingletonAnalyzer implements IAnalyzer {

    @Override
    public ISystemModel analyze(ISystemModel systemModel, IConfiguration config) {
        SingletonConfiguration singletonConfiguration = config.createConfiguration(SingletonConfiguration.class);
        return new SingletonSystemModel(systemModel, singletonConfiguration);
    }

}
