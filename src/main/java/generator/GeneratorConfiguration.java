package generator;

import config.Configurable;
import config.IConfiguration;

/**
 * A GeneratorConfiguration.
 * <p>
 * Created by lamd on 1/11/2017.
 */
public class GeneratorConfiguration implements Configurable {
    public static final String CONFIG_PATH = "graphviz.";
    public static final String NODE_SEP = CONFIG_PATH + "nodeSep";
    public static final String RANK_DIR = CONFIG_PATH + "rankDir";
    public static final String NODE_STYLE = CONFIG_PATH + "generator_node_style";

    private IConfiguration config;

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        this.config.setIfMissing(GeneratorConfiguration.NODE_SEP, "1.0");
        this.config.setIfMissing(GeneratorConfiguration.RANK_DIR, "BT");
        this.config.setIfMissing(GeneratorConfiguration.NODE_STYLE, "node [shape=record]");
    }

    /**
     * Returns the Node Seperation.
     *
     * @return Node Separation value.
     */
    public double getNodeSep() {
        return Double.parseDouble(this.config.getValue(GeneratorConfiguration.NODE_SEP));
    }

    /**
     * Returns either BT or TB depending on how you want the UML to show.
     *
     * @return BT or TB
     */
    public String getRankDir() {
        return this.config.getValue(GeneratorConfiguration.RANK_DIR);
    }

    /**
     * Returns the Graph styling for every Node.
     *
     * @return Node styling for entire graph.
     */
    public String getNodeStyle() {
        return this.config.getValue(GeneratorConfiguration.NODE_STYLE);
    }
}
