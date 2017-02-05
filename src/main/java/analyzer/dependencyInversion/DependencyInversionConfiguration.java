package analyzer.dependencyInversion;

import java.util.List;

import config.Configurable;
import config.IConfiguration;

/**
 * A Configurable for FavorComposition Analyzers.
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
        this.config.setIfMissing(WHITE_LIST, "java");
        this.config.setIfMissing(COLOR, "yellow");
    }

    public List<String> getWhiteList() {
        return this.config.getList(WHITE_LIST);
    }

    public String getColor() {
        return this.config.getValue(COLOR);
    }
}