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
public interface IEngineConfiguration extends Configurable {

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

    IGeneratorConfiguration getGeneratorConfiguration();

    IRunnerConfiguration getRunnerConfiguration();

    /**
     * Returns an Iterable of IAnalyzers.
     *
     * @return Iterable of IAnalyzers.
     */
    List<Class<? extends IAnalyzer>> getAnalyzers();
    
    IConfiguration getIConfiguration();
}
