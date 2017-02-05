package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adapter.relationParser.RelationParserAnalyzer;
import analyzer.utility.IAnalyzer;
import config.Configurable;
import config.IConfiguration;
import generator.GraphVizGenerator;
import generator.IGenerator;

/**
 * An IConfiguration concrete class. It uses Maps to store a variety of
 * configuration objects.
 * <p>
 * Created by lamd on 12/7/2016. Edited by fineral on 12/13/2016
 */
public class EngineConfiguration implements Configurable, IConfiguration {
    public static final String CONFIG_PATH = "engine.";
    public static final String GENERATOR_KEY = CONFIG_PATH + "generator_key";
    public static final String ANALYZER_KEY = CONFIG_PATH + "analyzer_key";

    private IConfiguration config;

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        this.config.setIfMissing(EngineConfiguration.GENERATOR_KEY, GraphVizGenerator.class.getName());
        this.config.setIfMissing(EngineConfiguration.ANALYZER_KEY, RelationParserAnalyzer.class.getName());
    }

    /**
     * Returns the IGenerator
     *
     * @return IGenerator object.
     */
    public IGenerator getGenerator() {
        String generatorClass = this.config.getValue(GENERATOR_KEY);
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
        analyzerNames.forEach((analyzerName) -> {
            IAnalyzer analyzer = IConfiguration.instantiateWithName(analyzerName, IAnalyzer.class);
            analyzers.add(analyzer);
        });
        return analyzers;
    }

    @Override
    public void set(String key, String value) {
        this.config.set(key, value);
    }

    @Override
    public void add(String key, String... value) {
        this.config.add(key, value);
    }

    @Override
    public List<String> getList(String key) {
        return this.config.getList(key);
    }

    @Override
    public String getValue(String key) {
        return config.getValue(key);
    }

    @Override
    public void setIfMissing(String key, String... value) {

        this.config.setIfMissing(key, value);
    }

    @Override
    public void populateMap(String directory, Map<String, Object> map) {
        this.config.populateMap(directory, map);
    }

    @Override
    public String toString() {
        return "Engine configuration with \n" + this.config.toString();
    }
}
