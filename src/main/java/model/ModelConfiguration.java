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
    public static final String IS_RECURSIVE_KEY = "isRecursive";
    public static final String CLASSES_KEY = "classes";
    public static final String BLACK_LIST = "blackList";
    public static final String VERBOSE = "verbose";
    public static final String SYNTHETIC = "synthetic";
    
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
        this.config.setIfMissing(ModelConfiguration.SYNTHETIC, "false");
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
    public Iterable<String> getBlackList() {
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

    /**
     * Returns the boolean value of the synthetic flag.
     *
     * @return true if the synthetic flag is on.
     */
    public boolean filterSynthetic() {
        return Boolean.parseBoolean(this.config.getValue(ModelConfiguration.SYNTHETIC));
    }
}
