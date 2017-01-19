package config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An IConfiguration concrete class. It uses Maps to store a variety of configuration objects.
 * <p>
 * Created by lamd on 12/7/2016. Edited by fineral on 12/13/2016
 */
public class Configuration implements IConfiguration {
    private static final String DELIMITER = " ";
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
        return config;
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
        return Collections.emptyList();
    }

    @Override
    public String getValue(String key) {
        return this.valueMap.get(key);
    }

}
