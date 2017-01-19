package config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import analyzer.classParser.AnalyzerClassParser;
import analyzer.relationParser.AnalyzerRelationParser;
import analyzer.utility.IAnalyzer;
import analyzer.utility.IAnalyzerConfiguration;
import generator.GraphVizGenerator;
import generator.IGenerator;
import generator.IGeneratorConfiguration;
import model.IModelConfiguration;
import runner.IRunnerConfiguration;

/**
 * An IConfiguration concrete class. It uses Maps to store a variety of
 * configuration objects.
 * <p>
 * Created by lamd on 12/7/2016. Edited by fineral on 12/13/2016
 */
public class EngineConfiguration implements IEngineConfiguration, IAnalyzerConfiguration {
    public static final String GENERATYR_KEY = "GENERATOR_KEY";
    public static final String ANALYZER_KEY = "ANALYZER_KEY";
    private IConfiguration config;
    private Map<Class<? extends IAnalyzer>, Object> analyzerToConfigurationMap = new HashMap<>();

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        config.set(GENERATYR_KEY, GraphVizGenerator.class.getName());
        addAnalyzerWithConfig(AnalyzerClassParser.class, config.createConfiguration(ClassParserConfiguration.class));
        addAnalyzer(AnalyzerClassParser.class);
        addAnalyzer(AnalyzerRelationParser.class);
    }

    @Override
    public Class<? extends IGenerator> getGenerator() {
        String generatorClass = config.getValue(GENERATYR_KEY);
        return IConfiguration.getClassFromName(generatorClass, IGenerator.class);
    }

    @Override
    public IModelConfiguration getModelConfiguration() {
        return config.createConfiguration(ModelConfiguration.class, ModelConfiguration.class);
    }

    /**
     * Sets the Configuration generator.
     *
     * @param generator
     *            IGenerator class to be set.
     */
    public void setGenerator(Class<? extends IGenerator> generator) {
        config.set(GENERATYR_KEY, generator.getName());
    }

    @Override
    public List<Class<? extends IAnalyzer>> getAnalyzers() {
        List<String> analyzerNames = config.getValues(ANALYZER_KEY);
        List<Class<? extends IAnalyzer>> analyzers = new ArrayList<>();
        for (String className : analyzerNames)
            analyzers.add(IConfiguration.getClassFromName(className, IAnalyzer.class));
        return analyzers;
    }

    @Override
    public void addAnalyzer(Class<? extends IAnalyzer> analyzerClass) {
        if (analyzerClass == null)
            throw new NullPointerException("Analyzer Class cannot be null");
        config.add(ANALYZER_KEY, analyzerClass.getName());
    }

    @Override
    public void addAnalyzerWithConfig(Class<? extends IAnalyzer> analyzerClass, Object configObject) {
        addAnalyzer(analyzerClass);
        this.analyzerToConfigurationMap.put(analyzerClass, configObject);
    }

    @Override
    public Object getConfigurationFor(Class<? extends IAnalyzer> analyzerClass) {
        return analyzerToConfigurationMap.get(analyzerClass);
    }

    @Override
    public void set(String key, String value) {
        config.set(key, value);
    }

    @Override
    public void setIfMissing(String key, String value) {
        if (config.getValue(key) == null) {
            config.set(key, value);
        }
    }

    @Override
    public void add(String key, String value) {
        config.add(key, value);
    }

    @Override
    public List<String> getValues(String key) {
        return config.getValues(key);
    }

    @Override
    public String getValue(String key) {
        return config.getValue(key);
    }

    @Override
    public IAnalyzerConfiguration getAnalyzerConfiguration() {
        return this;
    }

    @Override
    public IGeneratorConfiguration getGeneratorConfiguration() {
        return this.config.createConfiguration(GeneratorConfiguration.class, GeneratorConfiguration.class);
    }

    @Override
    public IRunnerConfiguration getRunnerConfiguration() {
        return this.config.createConfiguration(RunnerConfiguration.class, RunnerConfiguration.class);
    }

}
