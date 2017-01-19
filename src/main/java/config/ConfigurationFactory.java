package config;

/**
 *
 */
public interface ConfigurationFactory {
    /**
     * @return create the configuration
     * @throws Exception
     */
    IConfiguration create() throws Exception;
}
