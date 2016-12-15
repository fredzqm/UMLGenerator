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
     * @param sm   SystemModel containing all class information to diagram.
     * @param jobs A Collection of Patterns recognized.
     * @return the UML String.
     */
    String generate(ISystemModel sm, Iterable<IJob> jobs);

}
