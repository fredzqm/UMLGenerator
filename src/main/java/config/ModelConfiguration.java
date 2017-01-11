package config;

import model.IModelConfiguration;

/**
 * Created by lamd on 1/10/2017.
 */
public class ModelConfiguration implements IModelConfiguration, Configurable {
    public static final String CLASSES_KEY = "model_classes";
    public static final String IS_RECURSIVE_KEY = "model_is_recursive";

    private IConfiguration config;

    public ModelConfiguration() {
        this.config = null;
    }

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        this.config.addIfMissing(ModelConfiguration.CLASSES_KEY, "");
        this.config.setIfMissing(ModelConfiguration.IS_RECURSIVE_KEY, "false");
    }

    @Override
    public Iterable<String> getClasses() {
        return this.config.getValues(ModelConfiguration.CLASSES_KEY);
    }

    @Override
    public boolean isRecursive() {
        return Boolean.parseBoolean(this.config.getValue(ModelConfiguration.IS_RECURSIVE_KEY));
    }
}
