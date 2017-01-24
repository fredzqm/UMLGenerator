package analyzer.singleton;

import config.Configurable;
import config.IConfiguration;

/**
 * Created by lamd on 1/23/2017.
 */
public class SingletonConfiguration implements Configurable {
    public static final String CONFIG_PATH = "singleton.";
    public static final String SINGLETON_COLOR = CONFIG_PATH + "color";

    private IConfiguration config;

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        this.config.setIfMissing(SingletonConfiguration.SINGLETON_COLOR, "blue");
    }

    public String getSingletonColor() {
        return this.config.getValue(SingletonConfiguration.SINGLETON_COLOR);
    }
}
