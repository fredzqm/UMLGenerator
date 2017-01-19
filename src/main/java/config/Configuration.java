package config;

import analyzer.classParser.AnalyzerClassParser;
import analyzer.relationParser.AnalyzerRelationParser;
import analyzer.utility.IAnalyzer;
import generator.GraphVizGenerator;
import generator.IGenerator;
import utility.IFilter;
import utility.Modifier;

import java.util.*;

/**
 * An IConfiguration concrete class. It uses Maps to store a variety of configuration objects.
 * <p>
 * Created by lamd on 12/7/2016. Edited by fineral on 12/13/2016
 */
public class Configuration implements IEngineConfiguration {
    private static final String DELIMITER = " ";
    private static final String ANALYZER_LIST = "ANALYZER_LIST";
    private Map<String, String> valueMap;

    private Configuration() {
        this.valueMap = new HashMap<>();
    }

    /**
     * Returns an instance of the Configuration.
     *
     * @return Configuration instance.
     */
    public static Configuration getInstance() {
        Configuration config = new Configuration();
        config.add(ANALYZER_LIST , AnalyzerClassParser.class.getName());
        config.add(ANALYZER_LIST, AnalyzerRelationParser.class.getName());
        config.setFilter((x) -> true);
        return config;
    }

    @Override
    public Iterable<Class<? extends IAnalyzer>> getAnalyzers() {
        return this.analyzers;
    }

    @Override
    public Class<? extends IGenerator> getGenerator() {
        return this.generator;
    }

    @Override
    public Configurable createConfiguration(Class<? extends Configurable> configClass) {
        try {
            Configurable configurable = configClass.cast(configClass.newInstance());
            configurable.setup(this);
            return configurable;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(
                    "Configurable unable to be instantiated. Ensure that the Configurable has an empty constructor.",
                    e);
        }
    }

    @Override
    public IFilter<Modifier> getModifierFilter() {
        return this.modifierFilter;
    }

    @Override
    public void setFilter(IFilter<Modifier> modifierIFilter) {
        this.modifierFilter = modifierIFilter;
    }

    @Override
    public void setFilterIfMissing(IFilter<Modifier> modifierIFilter) {
        if (this.modifierFilter == null) {
            this.modifierFilter = modifierIFilter;
        }
    }

    @Override
    public void set(String key, String value) {
        this.valueMap.put(key, value);
    }

    @Override
    public void setIfMissing(String key, String value) {
        if (!this.valueMap.containsKey(key)) {
            this.valueMap.put(key, value);
        }
    }

    @Override
    public void add(String key, String value) {
        if (this.valueMap.containsKey(key)) {
            this.valueMap.put(key, String.format("%s%s%s", this.valueMap.get(key), Configuration.DELIMITER, value));
        } else {
            this.valueMap.put(key, value);
        }
    }

    @Override
    public List<String> getValues(String key) {
        if (this.valueMap.containsKey(key)) {
            String values = this.valueMap.get(key);
            return Arrays.asList(values.split(Configuration.DELIMITER));
        }
        return null;
    }

    @Override
    public String getValue(String key) {
        return this.valueMap.get(key);
    }

    public IEngineConfiguration getModelConfiguration() {
        return ;
    }

}
