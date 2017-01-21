package config;

import generator.IGeneratorConfiguration;

/**
 * A GeneratorConfiguration.
 * <p>
 * Created by lamd on 1/11/2017.
 */
public class GeneratorConfiguration implements IGeneratorConfiguration {
    public static final String NODE_SEP = "generator_node_sep";
    public static final String RANK_DIR = "generator_rank_dir";
    public static final String NODE_STYLE = "generator_node_style";

    private IConfiguration config;

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        this.config.setIfMissing(GeneratorConfiguration.NODE_SEP, "1.0");
        this.config.setIfMissing(GeneratorConfiguration.RANK_DIR, "BT");
        this.config.setIfMissing(GeneratorConfiguration.NODE_STYLE, "node [shape=record]");
    }
    
    @Override
    public String getConfigDir() {
        return "config";
    }

    @Override
    public double getNodeSep() {
        return Double.parseDouble(this.config.getValue(GeneratorConfiguration.NODE_SEP));
    }

    @Override
    public String getRankDir() {
        return this.config.getValue(GeneratorConfiguration.RANK_DIR);
    }

    @Override
    public String getNodeStyle() {
        return this.config.getValue(GeneratorConfiguration.NODE_STYLE);
    }

}
