package generator;

import config.IConfiguration;

/**
 * An Interface for Generators.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IGenerator {
    /**
     * * Creates a file formatted to display classes.
     *
     * @param graph
     *            SystemModel containing all class information to diagram and
     *            analyze result
     * @param config
     *            the configuration object
     * @return the UML String.
     */
    String generate(IGraph graph, IConfiguration config);
}
