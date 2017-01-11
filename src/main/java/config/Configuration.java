package config;

import analyzer.*;
import analyzerClassParser.*;
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
//    private Iterable<String> classes;
//    private String executablePath;
//    private String outputFormat;
//    private String outputDirectory;
//    private String fileName;
//    private double nodesep;
//    private IFilter<Modifier> modifierFilter;
//    private boolean isRecursive;
//    private String rankDir;
//    private String nodeStyle;
//    private Iterable<Class<? extends IAnalyzer>> analyzerls;
//    private Class<? extends IGenerator> generator;
//    private Class<? extends IParser<IClassModel>> classHeaderParser;
//    private Class<? extends IParser<IFieldModel>> fieldParser;
//    private Class<? extends IParser<IMethodModel>> methodParser;
//    private Class<? extends IParser<ITypeModel>> typeParser;
//    private Class<? extends IParser<Modifier>> modifierParser;

    private static final String DELIMITER = " ";

    private Map<String, Class> classMap;
    private Map<String, List<Class>> classesMap;
    private Map<String, String> valueMap;
    private Map<String, String> valuesMap;

    /**
     * TODO: Adam.
     *
     * @return
     */
    public static Configuration getInstance() {
        return new Configuration();
    }

    private Configuration() {
        this.classMap = new HashMap<>();
        this.classesMap = new HashMap<>();
        this.valueMap = new HashMap<>();
        this.valuesMap = new HashMap<>();
    }

    @Override
    public Configurable createConfiguration(Class<? extends Configurable> configClass) {
        try {
            Configurable configurable = configClass.newInstance();
            configurable.setup(this);
            return configurable;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Configurable unable to be instantiated. Ensure that the Configurable has an empty constructor.", e);
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
    public void add(String key, String value) {
        if (this.valuesMap.containsKey(key)) {
            this.valuesMap.put(key, String.format("%s%s%s", this.valueMap.get(key), DELIMITER, value));
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
        if (this.valuesMap.containsKey(key)) {
            String values = this.valuesMap.get(key);
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
