package config;

import java.util.ArrayList;
import java.util.List;

import analyzer.classParser.AnalyzerClassParser;
import analyzer.relationParser.AnalyzerRelationParser;
import analyzer.utility.IAnalyzer;
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
public class EngineConfiguration implements IEngineConfiguration {
    public static final String GENERATYR_KEY = "GENERATOR_KEY";
    public static final String ANALYZER_KEY = "ANALYZER_KEY";
    private IConfiguration config;

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        config.set(GENERATYR_KEY, GraphVizGenerator.class.getName());
        config.add(ANALYZER_KEY, AnalyzerClassParser.class.getName());
        config.add(ANALYZER_KEY, AnalyzerRelationParser.class.getName());
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

    @Override
    public IGeneratorConfiguration getGeneratorConfiguration() {
        return this.config.createConfiguration(GeneratorConfiguration.class);
    }

    @Override
    public IRunnerConfiguration getRunnerConfiguration() {
        return this.config.createConfiguration(RunnerConfiguration.class);
    }

    @Override
    public List<Class<? extends IAnalyzer>> getAnalyzers() {
        List<String> analyzerNames = config.getList(ANALYZER_KEY);
        List<Class<? extends IAnalyzer>> analyzers = new ArrayList<>();
        for (String className : analyzerNames)
            analyzers.add(IConfiguration.getClassFromName(className, IAnalyzer.class));
        return analyzers;
    }

    @Override
    public IConfiguration getIConfiguration() {
        return config;
    }

}
