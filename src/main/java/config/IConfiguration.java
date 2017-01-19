package config;

import analyzer.utility.IAnalyzer;
import generator.IGenerator;
import utility.IFilter;
import utility.Modifier;

/**
 * An interface for Configuration object that stores values to be used in generation processes.
 */
public interface IConfiguration {
    /**
     * Constructs the given configurable object.
     *
     * @param configClass class of the Configurable object.
     * @return Configurable object.
     */
    Configurable createConfiguration(Class<? extends Configurable> configClass);

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
     * Returns an Iterable of String corresponding to the given key.
     * It should be used with declared static constants of a concrete class.
     *
     * @param key String key for desired Iterable of Strings.
     * @return Iterable of String corresponding to that key.
     */
    Iterable<String> getValues(String key);

    /**
     * Return the String value associated with the given key.
     *
     * @param key String key for desired String value.
     * @return String value corresponding to that key.
     */
    String getValue(String key);

    /**
     * Returns an Iterable of IAnalyzers.
     *
     * @return Iterable of IAnalyzers.
     */
    Iterable<Class<? extends IAnalyzer>> getAnalyzers();

    /**
     * Returns the IGenerator stored.
     *
     * @return IGenerator object.
     */
    Class<? extends IGenerator> getGenerator();

    /**
     * Sets the given key and value if the key does not exist or has not been set.
     *
     * @param key   String key value.
     * @param value String value corresponding to the key.
     */
    void setIfMissing(String key, String value);

    /**
     * Set the modifier filter.
     *
     * @param modifierIFilter IFilter to be set.
     */
    void setFilter(IFilter<Modifier> modifierIFilter);

    /**
     * Set the modifier filter if it has not been set.
     *
     * @param modifierIFilter IFilter to be set.
     */
    void setFilterIfMissing(IFilter<Modifier> modifierIFilter);

    /**
     * Returns the ModifierFilter.
     *
     * @return IFilter<Modifier> stored.
     */
    IFilter<Modifier> getModifierFilter();
}
