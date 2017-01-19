package config;

import java.util.List;

import analyzer.utility.IAnalyzer;
import generator.IGenerator;
import generator.IGeneratorConfiguration;
import model.IModelConfiguration;
import runner.IRunnerConfiguration;

/**
 * An interface for Configuration object that stores values to be used in
 * generation processes.
 */
public interface IEngineConfiguration extends IConfiguration, Configurable {

    /**
     * 
     * @return the ModelConfiguration to get an instance of the model
     */
    IModelConfiguration getModelConfiguration();

    /**
     * Returns the IGenerator stored.
     *
     * @return IGenerator object.
     */
    Class<? extends IGenerator> getGenerator();

    /**
     * Sets the Configuration generator.
     *
     * @param generator
     *            IGenerator class to be set.
     */
    void setGenerator(Class<? extends IGenerator> generator);

    IGeneratorConfiguration getGeneratorConfiguration();

    IRunnerConfiguration getRunnerConfiguration();

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
