package config;

import java.util.*;

/**
 * An IConfiguration concrete class. It uses Maps to store a variety of
 * configuration objects.
 * <p>
 * Created by lamd on 12/7/2016. Edited by fineral on 12/13/2016
 */
public class Configuration implements IConfiguration {
    private static final String DELIMITER = " ";
    private Map<String, String> valueMap;
    private String currentDir = "";

    private Configuration() {
        this.valueMap = new HashMap<>();
    }

    /**
     * Returns an instance of the Configuration.
     *
     * @return Configuration instance.
     */
    public static Configuration getInstance() {
        return new Configuration();
    }

    private void putToMap(String key, String value) {
        this.valueMap.put(currentDir + key, value);
    }

    private boolean containsKey(String key) {
        return this.valueMap.containsKey(key);
    }

    private String getFromMap(String key) {
        return this.valueMap.get(key);
    }

    @Override
    public void setUpDir(String directory) {
        if (directory.length() == 0)
            currentDir = "";
        else
            currentDir = directory + ".";
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
        String x = String.join(DELIMITER, values);
        if (containsKey(key)) {
            String value = String.format("%s%s%s", getFromMap(key), Configuration.DELIMITER, x);
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
            return Arrays.asList(values.split(Configuration.DELIMITER));
        }
        return Collections.emptyList();
    }

    @Override
    public String getValue(String key) {
        return getFromMap(key);
    }

}
