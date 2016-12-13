package generator;

import configs.IConfiguration;

/**
 * An Interface for Generator Configuration.
 * <p>
 * Created by lamd on 12/12/2016.
 */
public interface IGeneratorConfiguration extends IConfiguration {
    /**
     * Returns the Node Seperation.
     *
     * @return Node Separation value.
     */
    double getNodeSep();

    /**
     * Sets the Node Separation value.
     *
     * @param nodeSep new Node Separation value.
     */
    void setNodeSep(double nodeSep);
}
