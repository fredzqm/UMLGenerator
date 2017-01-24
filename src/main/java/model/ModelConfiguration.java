package model;

import java.util.List;

import config.Configurable;
import config.IConfiguration;

/**
 * A ModelConfiguration.
 * <p>
 * Created by lamd on 1/10/2017.
 */
public class ModelConfiguration implements Configurable {
    public static final String CONFIG_PATH = "model.";
    public static final String IS_RECURSIVE_KEY = CONFIG_PATH + "isRecursive";
    public static final String CLASSES_KEY = CONFIG_PATH + "classes";
    public static final String BLACK_LIST = CONFIG_PATH + "blackList";
    public static final String VERBOSE = CONFIG_PATH + "verbose";

    private IConfiguration config;

    /**
     * Empty constructor for newInstance calls.
     */
    public ModelConfiguration() {
        this.config = null;
    }

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        this.config.setIfMissing(ModelConfiguration.IS_RECURSIVE_KEY, "false");
        this.config.setIfMissing(ModelConfiguration.VERBOSE, "false");
    }

    /**
     * Return the name of classes that the model needs to analyzer
     */
    public List<String> getClasses() {
        return this.config.getList(ModelConfiguration.CLASSES_KEY);
    }

    /**
     * @return true if the Model should recursively explore related classes
     */
    public boolean isRecursive() {
        return Boolean.parseBoolean(this.config.getValue(ModelConfiguration.IS_RECURSIVE_KEY));
    }

    /**
     * Returns an Iterable of Black list class names.
     *
     * @return Iterable of black list class names.
     */
    public List<String> getBlackList() {
        return this.config.getList(ModelConfiguration.BLACK_LIST);
    }

    /**
     * Returns the boolean value of the verbose flag.
     *
     * @return true if the verbose flag is on.
     */
    public boolean isVerbose() {
        return Boolean.parseBoolean(this.config.getValue(ModelConfiguration.VERBOSE));
    }

}
