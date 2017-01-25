package config;

import java.util.*;
import java.util.Map.Entry;

/**
 * An IConfiguration concrete class. It uses Maps to store a variety of
 * configuration objects.
 * <p>
 * Created by lamd on 12/7/2016. Edited by fineral on 12/13/2016
 */
public class Configuration implements IConfiguration {
    private static final String LIST_DELIMITER = " ";
    private static final String DIRECTORY_DELIMITER = ".";
    private Map<String, String> valueMap;

    private Configuration() {
        this.valueMap = new HashMap<>();
    }

    /**
     * Returns an instance of the Configuration.
     * *
     *
     * @return Configuration instance.
     */
    public static Configuration getInstance() {
        return new Configuration();
    }

    public void populateMap(String directory, Map<String, Object> map) {
        for (Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String configKey = directory.length() == 0 ? key : directory + DIRECTORY_DELIMITER + key;
            Object value = entry.getValue();
            if (value == null) {
                putToMap(configKey, null);
            } else if (value instanceof Map) {
                Map<String, Object> innerMap = null;
                try {
                    innerMap = (Map<String, Object>) value;
                } catch (ClassCastException e) {
                    throw new RuntimeException("inner map has to be Map<String,Object>, but was " + innerMap, e);
                }
                populateMap(configKey, innerMap);
            } else if (value instanceof List) {
                List<String> innerList = null;
                try {
                    innerList = (List<String>) value;
                } catch (ClassCastException e) {
                    throw new RuntimeException("inner list has to be Map<String,Object>, but was " + innerList, e);
                }
                String v = String.join(LIST_DELIMITER, innerList);
                putToMap(configKey, v);
            } else {
                putToMap(configKey, value.toString());
            }
        }
    }

    private void putToMap(String key, String value) {
        this.valueMap.put(key, value);
    }

    private boolean containsKey(String key) {
        return this.valueMap.containsKey(key);
    }

    private String getFromMap(String key) {
        return this.valueMap.get(key);
    }

    @Override
    public void set(String key, String value) {
        putToMap(key, value);
    }

    @Override
    public void setIfMissing(String key, String value) {
        if (key != null && !containsKey(key)) {
            putToMap(key, value);
        }
    }

    @Override
    public void add(String key, String... values) {
        String x = String.join(LIST_DELIMITER, values);
        if (containsKey(key)) {
            String value = String.format("%s%s%s", getFromMap(key), Configuration.LIST_DELIMITER, x);
            putToMap(key, value);
        } else {
            putToMap(key, x);
        }
    }

    @Override
    public List<String> getList(String key) {
        if (containsKey(key)) {
            String values = getFromMap(key);
            return Arrays.asList(values.split(Configuration.LIST_DELIMITER));
        }
        return Collections.emptyList();
    }

    @Override
    public String getValue(String key) {
        return getFromMap(key);
    }

    @Override
    public String toString() {
        return valueMap.toString();
    }
}
