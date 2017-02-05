package analyzer.dependencyInversion;

import analyzer.utility.IAnalyzer;
import analyzer.utility.ISystemModel;
import config.IConfiguration;

/**
 * Created by lamd on 1/14/2017.
 */
public class DependencyInversionAnalyzer implements IAnalyzer {

    @Override
    public ISystemModel analyze(ISystemModel systemModel, IConfiguration config) {
        DependencyInversionConfiguration favorComConfig = config
                .createConfiguration(DependencyInversionConfiguration.class);
        return new DependencyInversionSystemModel(systemModel, favorComConfig);
    }

}
