package config;

import analyzer.utility.IAnalyzerConfiguration;
import generator.IGenerator;
import generator.IGeneratorConfiguration;
import model.IModelConfiguration;
import runner.IRunnerConfiguration;

/**
 * An interface for Configuration object that stores values to be used in
 * generation processes.
 */
public interface IEngineConfiguration extends IConfiguration, Configurable, IAnalyzerConfiguration {

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

    IAnalyzerConfiguration getAnalyzerConfiguration();

    IGeneratorConfiguration getGeneratorConfiguration();

    IRunnerConfiguration getRunnerConfiguration();

}
