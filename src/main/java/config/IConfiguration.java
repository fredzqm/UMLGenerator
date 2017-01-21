package config;

import java.util.List;
import java.util.Map;

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
    void add(String key, String... value);

    /**
     * 
     * @param directory
     *            the directory to add this map
     * @param map
     *            the map to be added
     */
    void populateMap(String directory, Map<String, Object> map);

    /**
     * Returns an Iterable of String corresponding to the given key. It should
     * be used with declared static constants of a concrete class.
     *
     * @param key
     *            String key for desired Iterable of Strings.
     * @return Iterable of String corresponding to that key.
     */
    List<String> getList(String key);

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
     * Sets the given key and value if the key does not exist or has not been
     * set.
     *
     * @param key
     *            String key value.
     * @param value
     *            String value corresponding to the key.
     */
    void addIfMissing(String key, String... value);

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

    /**
     * This method acts as the factory for all configuration As long as it
     * implements the {@link Configurable} interface, we can instantiate it, and
     * fill the default configuration if not otherwise specified already
     *
     * @param configClass
     *            the class we want to create
     * @param returnType
     *            the planned return type, if returnType and configClass are the
     *            same, consider use convenient method
     *            {@link IConfiguration#createConfiguration(Class)}
     * @return the configuration instance
     */
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

    /**
     * This acts as a factory method for any java object, it gets the Class with
     * {@link Class#forName(String)} and then calls {@link Class#newInstance()}
     * to instantiate it. It handles all sorts of exception that can happen
     * while creating these objects, and throw proper RuntimeException
     * <p>
     * This method is helpful when we want to create an instance based on the
     * configuration string.
     *
     * @param className
     *            the name of class we want to instantiate
     * @param returType
     *            the planned return type
     * @return the created object
     */
    static <T> T instantiateWithName(String className, Class<T> returType) {
        try {
            Class<?> forName = Class.forName(className);
            if (!returType.isAssignableFrom(forName))
                throw new RuntimeException(forName + " cannot be casted to " + returType);
            return (T) forName.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(className + " is not a valid class", e);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(className + " does not have an empty constructor", e);
        }
    }

}
