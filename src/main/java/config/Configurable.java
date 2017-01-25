package config;

/**
 * An interface of Configuration types that need to interact with a primary
 * Configuration type.
 * <p>
 * Created by lamd on 1/10/2017.
 */
public interface Configurable {
    /**
     * Sets up the given IConfiguration Objects. Sets default values missing.
     *
     * @param config IConfiguration object to setup.
     */
    void setup(IConfiguration config);

}
