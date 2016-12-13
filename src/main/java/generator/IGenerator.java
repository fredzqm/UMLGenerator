package generator;

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
     * @param config {@link IGeneratorConfiguration} for the Generator to follow.
     * @param jobs   A Collection of Patterns recognized.
     * @return the UML String.
     */
    String generate(IGeneratorSystemModel sm, IGeneratorConfiguration config, Iterable<IJob> jobs);

}
