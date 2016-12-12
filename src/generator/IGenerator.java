package generator;

import analyzer.Job;
import configs.Configuration;

import java.util.Collection;

/**
 * An Interface for Generators.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IGenerator {
    /**
     * Creates a file formatted to display classes.
     *
     * @param sm     SystemModel containing all class information to diagram.
     * @param config Configuration for the Generator to follow.
     * @param jobs   A Collection of Patterns recognized.
     */
    void generate(ISystemModel sm, Configuration config, Collection<Job> jobs);
}
