package config;

import java.util.List;

/**
 * An interface for Configuration object that stores values to be used in
 * generation processes.
 */
public interface IConfiguration {

    /**
     * Sets the String key to the String value.
     *
     * @param key
     *            String key to set value for.
     * @param value
     *            String value of the key.
     */
    void set(String key, String value);

    /**
     * Adds to the String key to a list of String values.
     *
     * @param key
     *            String key to append value String to.
     * @param value
     *            String value to be appened.
     */
    void add(String key, String value);

    /**
     * Returns an Iterable of String corresponding to the given key. It should
     * be used with declared static constants of a concrete class.
     *
     * @param key
     *            String key for desired Iterable of Strings.
     * @return Iterable of String corresponding to that key.
     */
    List<String> getValues(String key);

    /**
     * Return the String value associated with the given key.
     *
     * @param key
     *            String key for desired String value.
     * @return String value corresponding to that key.
     */
    String getValue(String key);

    /**
     * Sets the given key and value if the key does not exist or has not been
     * set.
     *
     * @param key
     *            String key value.
     * @param value
     *            String value corresponding to the key.
     */
    void setIfMissing(String key, String value);

    /**
     * Constructs the given configurable object.
     *
     * @param configClass
     *            class of the Configurable object.
     * @return Configurable object.
     */
    default <T extends Configurable> T createConfiguration(Class<T> configClass) {
        return createConfiguration(configClass, configClass);
    }

    default <T extends Configurable> T createConfiguration(Class<? extends Configurable> configClass,
            Class<T> returnType) {
        if (!returnType.isAssignableFrom(configClass))
            throw new RuntimeException(configClass + " cannot be casted to " + returnType);
        try {
            Configurable configurable = configClass.cast(configClass.newInstance());
            configurable.setup(this);
            return returnType.cast(configurable);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(
                    "Configurable unable to be instantiated. Ensure that the Configurable has an empty constructor.",
                    e);
        }
    }

    static <T> Class<T> getClassFromName(String className, Class<T> returType) {
        try {
            Class<?> forName = Class.forName(className);
            if (returType.isAssignableFrom(forName))
                return (Class<T>) forName;
            throw new RuntimeException(forName + " cannot be casted to " + returType);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(className + " is not a valid class", e);
        }
    }

}
