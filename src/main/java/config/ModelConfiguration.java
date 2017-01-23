package config;

import model.IModelConfiguration;

import java.util.List;

/**
 * A ModelConfiguration.
 * <p>
 * Created by lamd on 1/10/2017.
 */
public class ModelConfiguration implements IModelConfiguration, Configurable {
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
        this.config.addIfMissing(CLASSES_KEY, "");
        this.config.addIfMissing(BLACK_LIST, "");
        this.config.addIfMissing(VERBOSE, "false");
        this.config.addIfMissing(SYNTHETIC, "false");
    }

    @Override
    public List<String> getClasses() {
        return this.config.getList(ModelConfiguration.CLASSES_KEY);
    }

    @Override
    public boolean isRecursive() {
        return Boolean.parseBoolean(this.config.getValue(ModelConfiguration.IS_RECURSIVE_KEY));
    }
}
