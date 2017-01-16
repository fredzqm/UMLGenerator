package analyzer.utility;

public interface IAnalyzerConfiguration {
    /**
     * Returns the Analyzer configuration to be cast.
     *
     * @param analyzerClass Analyzer class whose configuration is being fetched.
     * @return the specific configuration object for this analyzer
     */
    Object getConfigurationFor(Class<? extends IAnalyzer> analyzerClass);

    /**
     * Stores the correspondance of analyzerClass with Config.
     *
     * @param analyzerClass IAnalyzer to link to correspondance.
     * @param config        A Configuration Object.
     */
    void mapAnalyzerToConfig(Class<? extends IAnalyzer> analyzerClass, Object config);
}
