package analyzer.favorComposition;

import config.Configurable;
import config.IConfiguration;

/**
 * A Configurable for FavorComposition Analyzers.
 * <p>
 * Created by lamd on 1/23/2017.
 */
public class FavorCompositionConfiguration implements Configurable {
    public static final String CONFIG_PATH = "favorComposition.";
    public static final String COLOR = CONFIG_PATH + "color";

    private IConfiguration config;

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        this.config.setIfMissing(FavorCompositionConfiguration.COLOR, "orange");
    }

    public String getFavorComColor() {
        return this.config.getValue(FavorCompositionConfiguration.COLOR);
    }
}
