package app;

import analyzer.classParser.AnalyzerClassParser;
import analyzer.relationParser.AnalyzerRelationParser;
import analyzer.utility.IAnalyzer;
import config.Configurable;
import config.IConfiguration;
import generator.GraphVizGenerator;
import generator.IGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An IConfiguration concrete class. It uses Maps to store a variety of
 * configuration objects.
 * <p>
 * Created by lamd on 12/7/2016. Edited by fineral on 12/13/2016
 */
public class EngineConfiguration implements Configurable, IConfiguration {
    public static final String GENERATOR_KEY = "GENERATOR_KEY";
    public static final String ANALYZER_KEY = "ANALYZER_KEY";
    private IConfiguration config;

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        config.setIfMissing(GENERATOR_KEY, GraphVizGenerator.class.getName());
        config.add(ANALYZER_KEY, AnalyzerClassParser.class.getName(), AnalyzerRelationParser.class.getName());
    }

    /**
     * Returns the IGenerator
     *
     * @return IGenerator object.
     */
    public IGenerator getGenerator() {
        String generatorClass = config.getValue(GENERATOR_KEY);
        return IConfiguration.instantiateWithName(generatorClass, IGenerator.class);
    }

    /**
     * Returns an Iterable of IAnalyzers.
     *
     * @return Iterable of IAnalyzers.
     */
    public List<IAnalyzer> getAnalyzers() {
        List<String> analyzerNames = config.getList(ANALYZER_KEY);
        List<IAnalyzer> analyzers = new ArrayList<>();
        for (String className : analyzerNames) {
            IAnalyzer analyzer = IConfiguration.instantiateWithName(className, IAnalyzer.class);
            analyzers.add(analyzer);
        }
        return analyzers;
    }

    @Override
    public void set(String key, String value) {
        config.set(key, value);
    }

    @Override
    public void add(String key, String... value) {
        config.add(key, value);
    }

    @Override
    public List<String> getList(String key) {
        return config.getList(key);
    }

    @Override
    public String getValue(String key) {
        return config.getValue(key);
    }

    @Override
    public void setIfMissing(String key, String value) {
        config.setIfMissing(key, value);
    }

    @Override
    public void populateMap(String directory, Map<String, Object> map) {
        config.populateMap(directory, map);
    }

    @Override
    public String toString() {
        return "Engine configuration with \n" + config.toString();
    }
}
