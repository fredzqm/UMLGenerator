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
        this.config.setIfMissing(ModelConfiguration.VERBOSE, "false");
        this.config.setIfMissing(ModelConfiguration.SYNTHETIC, "false");
    }

    @Override
    public List<String> getClasses() {
        return this.config.getList(ModelConfiguration.CLASSES_KEY);
    }

    @Override
    public boolean isRecursive() {
        return Boolean.parseBoolean(this.config.getValue(ModelConfiguration.IS_RECURSIVE_KEY));
    }

    @Override
    public Iterable<String> getBlackList() {
        return this.config.getList(ModelConfiguration.BLACK_LIST);
    }

    @Override
    public boolean isVerbose() {
        return Boolean.parseBoolean(this.config.getValue(ModelConfiguration.VERBOSE));
    }

    @Override
    public boolean filterSynthetic() {
        return Boolean.parseBoolean(this.config.getValue(ModelConfiguration.SYNTHETIC));
    }
}
