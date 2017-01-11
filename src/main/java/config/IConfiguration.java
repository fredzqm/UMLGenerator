package config;

public interface IConfiguration {
    /**
     * Constructs the given configurable object.
     *
     * @param configClass class of the Configurable object.
     * @return Configurable object.
     */
    Configurable createConfiguration(Class<? extends Configurable> configClass);

    /**
     * Sets the key value to the given class value.
     *
     * @param key   String key to set within the map.
     * @param value Class to map the key to.
     */
    void set(String key, Class value);

    /**
     * Sets the String key to the String value.
     *
     * @param key   String key to set value for.
     * @param value String value of the key.
     */
    void set(String key, String value);

    /**
     * Adds to the String key to a list of String values.
     *
     * @param key   String key to append value String to.
     * @param value String value to be appened.
     */
    void add(String key, String value);

    /**
     * Adds the String key to a list of Classes.
     *
     * @param key   String key to append value to.
     * @param value String value to be appened.
     */
    void add(String key, Class value);

    Iterable<String> getValues(String key);
    Iterable<Class> getClasses(String key);
    String getValue(String key);
    Class getClass(String key);
}
