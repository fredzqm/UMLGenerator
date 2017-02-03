package analyzer.dependencyInversion;

import java.util.List;

import analyzer.favorComposition.FavorCompositionConfiguration;
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
    public static final String FAVOR_COM_COLOR = CONFIG_PATH + "color";

    private IConfiguration config;

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        this.config.setIfMissing(DependencyInversionConfiguration.WHITE_LIST, "java");
    }

    public List<String> getBlackList() {
        return this.config.getList(DependencyInversionConfiguration.WHITE_LIST);
    }

    public String getColor() {
        return this.config.getValue(FavorCompositionConfiguration.COLOR);
    }
}