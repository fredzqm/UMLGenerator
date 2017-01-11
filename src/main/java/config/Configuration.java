package config;

import analyzer.IAnalyzer;
import analyzerClassParser.AnalyzerClassParser;
import analyzerRelationParser.AnalyzerRelationParser;
import generator.GraphVizGenerator;
import generator.IGenerator;
import utility.IFilter;
import utility.Modifier;

import java.util.*;

/**
 * TODO: Adam Document.
 * <p>
 * Created by lamd on 12/7/2016. Edited by fineral on 12/13/2016
 */
public class Configuration implements IConfiguration {
    private static final String DELIMITER = " ";

    private Map<String, Class> classMap;
    private Map<String, List<Class>> classesMap;
    private Map<String, String> valueMap;
    private Map<Class<? extends IAnalyzer>, Object> analyzerToConfigurationMap;
    private List<Class<? extends IAnalyzer>> analyzers;
    private Class<? extends IGenerator> generator;
    private IFilter<Modifier> modifierFilter;

    private Configuration() {
        this.classMap = new HashMap<>();
        this.classesMap = new HashMap<>();
        this.valueMap = new HashMap<>();
        // this.valuesMap = new HashMap<>();
        this.analyzerToConfigurationMap = new HashMap<>();
        this.modifierFilter = null;

        this.generator = GraphVizGenerator.class;
        this.analyzers = new ArrayList<>();
    }

    /**
     * Returns an instance of the Configuration.
     *
     * @return Configuration instance.
     */
    public static Configuration getInstance() {
        Configuration config = new Configuration();
        config.addAnalyzer(AnalyzerRelationParser.class);
        config.addAnalyzer(AnalyzerClassParser.class);
        config.setFilter((x) -> true);
        return config;
    }

    @Override
    public Iterable<Class<? extends IAnalyzer>> getAnalyzers() {
        return this.analyzers;
    }

    @Override
    public void addAnalyzer(Class<? extends IAnalyzer> analyzer) {
        this.analyzers.add(analyzer);
    }

    @Override
    public void removeAnalyzer(Class<? extends IAnalyzer> analyzer) {
        this.analyzers.remove(analyzer);
    }

    @Override
    public void mapAnalyzerConfig(Class<? extends IAnalyzer> analyzerClass, Object config) {
        if (analyzerClass == null) {
            throw new NullPointerException("Analyzer Class cannot be null");
        }

        this.analyzerToConfigurationMap.put(analyzerClass, config);
    }

    @Override
    public Object getAnalyzerConfig(Class<? extends IAnalyzer> analyzerClass) {
        return this.analyzerToConfigurationMap.get(analyzerClass);
    }

    @Override
    public Class<? extends IGenerator> getGenerator() {
        return this.generator;
    }

    public void setGenerator(Class<? extends IGenerator> generator) {
        this.generator = generator;
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
    public void set(String key, Class value) {
        this.classMap.put(key, value);
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
    public void setIfMissing(String key, Class value) {
        if (!this.classMap.containsKey(key)) {
            this.classMap.put(key, value);
        }
    }

    @Override
    public void addIfMissing(String key, String value) {
        if (!this.valueMap.containsKey(key)) {
            this.valueMap.put(key, value);
        }
    }

    @Override
    public void addIfMissing(String key, Class value) {
        if (!this.classesMap.containsKey(key)) {
            this.classesMap.put(key, Collections.singletonList(value));
        }
    }

    @Override
    public void add(String key, String value) {
        if (this.valueMap.containsKey(key)) {
            this.valueMap.put(key, String.format("%s%s%s", this.valueMap.get(key), DELIMITER, value));
        } else {
            this.valueMap.put(key, value);
        }
    }

    @Override
    public void add(String key, Class value) {
        List<Class> classesList;
        if (this.classesMap.containsKey(key)) {
            classesList = this.classesMap.get(key);
            classesList.add(value);
        } else {
            classesList = new ArrayList<>();
        }
        this.classesMap.put(key, classesList);
    }

    @Override
    public Iterable<String> getValues(String key) {
        if (this.valueMap.containsKey(key)) {
            String values = this.valueMap.get(key);
            return Arrays.asList(values.split(DELIMITER));
        }
        return null;
    }

    @Override
    public Iterable<Class> getClasses(String key) {
        return this.classesMap.get(key);
    }

    @Override
    public String getValue(String key) {
        return this.valueMap.get(key);
    }

    @Override
    public Class getClass(String key) {
        return this.classMap.get(key);
    }
}
