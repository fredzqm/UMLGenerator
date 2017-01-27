package analyzer.favorComposition;

import analyzer.utility.IAnalyzer;
import analyzer.utility.ISystemModel;
import config.IConfiguration;

/**
 * FavorComposition Analyzer.
 * <p>
 * Created by lamd on 1/14/2017.
 */
public class FavorCompositionAnalyzer implements IAnalyzer {
    @Override
    public ISystemModel analyze(ISystemModel systemModel, IConfiguration config) {
        FavorCompositionConfiguration favorComConfig = config.createConfiguration(FavorCompositionConfiguration.class);
        return new FavorCompositionSystemModel(systemModel, favorComConfig);
    }
}
