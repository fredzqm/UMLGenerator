package analyzer.utility;

import java.util.List;

import config.Configurable;

public interface IAnalyzerConfiguration extends Configurable{
    /**
     * Returns the Analyzer configuration to be cast.
     *
     * @param analyzerClass Analyzer class whose configuration is being fetched.
     * @return the specific configuration object for this analyzer
     */
    Object getConfigurationFor(Class<? extends IAnalyzer> analyzerClass);

    /**
     * Returns an Iterable of IAnalyzers.
     *
     * @return Iterable of IAnalyzers.
     */
    List<Class<? extends IAnalyzer>> getAnalyzers();

    /**
     * Add Analyzers to be stored within object.
     *
     * @param analyzer
     *            IAnalyzer to be stored.
     */
    void addAnalyzer(Class<? extends IAnalyzer> analyzer);

    /**
     * Maps an AnalyzerConfiguration object to the given AnalyzerClass.
     *
     * @param analyzerClass
     *            IAnalyzer being mapped.
     * @param config
     *            Object to be mapped.
     */
    void addAnalyzerWithConfig(Class<? extends IAnalyzer> analyzerClass, Object config);
}
