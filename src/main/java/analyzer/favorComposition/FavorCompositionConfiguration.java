package analyzer.favorComposition;

import config.Configurable;
import config.IConfiguration;

/**
 * A Configurable for FavorComposition Analyzers.
 * <p>
 * Created by lamd on 1/23/2017.
 */
public class FavorCompositionConfiguration implements Configurable {
    public static final String FAVOR_COM_COLOR = "favorComColor";

    private IConfiguration config;

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        this.config.setIfMissing(FavorCompositionConfiguration.FAVOR_COM_COLOR, "orange");
    }

    public String getFavorComColor() {
        return this.config.getValue(FavorCompositionConfiguration.FAVOR_COM_COLOR);
    }
}
