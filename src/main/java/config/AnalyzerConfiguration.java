package config;

import analyzer.classParser.AnalyzerClassParser;
import analyzer.utility.IAnalyzer;
import analyzer.utility.IAnalyzerConfiguration;

/**
 * An AnalyzerConfiguration.
 * <p>
 * Created by lamd on 1/9/2017.
 */
public class AnalyzerConfiguration implements IAnalyzerConfiguration, Configurable {
    private IConfiguration config;

    /**
     * Empty constructor for newInstance calls.
     */
    public AnalyzerConfiguration() {
        this.config = null;
    }

    @Override
    public Object getConfigurationFor(Class<? extends IAnalyzer> analyzerClass) {
        return this.config.getAnalyzerConfig(analyzerClass);
    }

    @Override
    public void mapAnalyzerToConfig(Class<? extends IAnalyzer> analyzerClass, Object config) {
        this.config.mapAnalyzerConfig(analyzerClass, config);
    }

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        this.mapAnalyzerToConfig(AnalyzerClassParser.class, config.createConfiguration(ClassParserConfiguration.class));
    }
}