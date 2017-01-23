package config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * An IConfiguration concrete class. It uses Maps to store a variety of
 * configuration objects.
 * <p>
 * Created by lamd on 12/7/2016. Edited by fineral on 12/13/2016
 */
public class Configuration implements IConfiguration {
    private static final String LIST_DELIMITER = " ";
    private static final String DIRECTORY_DELIMITER = "/";
    private Map<String, String> valueMap;

    private Configuration() {
        this.valueMap = new HashMap<>();
    }

    /**
     * Returns an instance of the Configuration.
     **
     * @return Configuration instance.
     */
    public static Configuration getInstance() {
        return new Configuration();
    }

    public void populateMap(String directory, Map<String, Object> map) {
        for (Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String configKey = key + DIRECTORY_DELIMITER + directory;
            Object value = entry.getValue();
            if (value == null) {
                putToMap(configKey, null);
            } else if (value instanceof Map) {
                try {
                    Map<String, Object> innerMap = (Map<String, Object>) value;
                    populateMap(configKey, innerMap);
                } catch (ClassCastException e) {
                    throw new RuntimeException("inner map has to be Map<String,Object>", e);
                }
            } else if (value instanceof List) {
                try {
                    List<String> innerList = (List<String>) value;
                    String v = String.join(LIST_DELIMITER, innerList);
                    putToMap(configKey, v);
                } catch (ClassCastException e) {
                    throw new RuntimeException("inner list has to be Map<String,Object>", e);
                }
            } else {
                try {
                    String v = (String) value;
                    putToMap(configKey, v);
                } catch (ClassCastException e) {
                    throw new RuntimeException("inner value has to be Map<String,Object>", e);
                }
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
    public void addIfMissing(String key, String... value) {
        if (value != null && value.length > 0 && !containsKey(key)) {
            add(key, value);
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

}
