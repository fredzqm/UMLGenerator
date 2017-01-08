package analyzer;

public interface IAnalyzerConfiguration {
    /**
     * Returns the Analyzer configuration to be cast.
     *
     * @param analyzerClass Analyzer class whose configuration is being fetched.
     * @return the specific configuration object for this analyzer
     */
    Object getConfigurationFor(Class<? extends IAnalyzer> analyzerClass);
}
