package analyzer.dependencyInversion;

import config.Configurable;
import config.IConfiguration;

import java.util.List;

/**
 * A Configurable for Dependency Inversion Analyzers.
 * <p>
 * Created by lamd on 1/23/2017.
 */
public class DependencyInversionConfiguration implements Configurable {
    public static final String CONFIG_PATH = "dependencyInversion.";
    public static final String WHITE_LIST = CONFIG_PATH + "whiteList";
    public static final String COLOR = CONFIG_PATH + "color";

    private IConfiguration config;

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        this.config.setIfMissing(DependencyInversionConfiguration.WHITE_LIST, "java");
        this.config.setIfMissing(DependencyInversionConfiguration.COLOR, "yellow");
    }

    public List<String> getWhiteList() {
        return this.config.getList(DependencyInversionConfiguration.WHITE_LIST);
    }

    public String getColor() {
        return this.config.getValue(DependencyInversionConfiguration.COLOR);
    }
}