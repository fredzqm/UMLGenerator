package config;

/**
 * An interface of Configuration types that need to interact with a primary Configuration type.
 * <p>
 * Created by lamd on 1/10/2017.
 */
public interface Configurable {
    void setup(IConfiguration config);
}
