package generator;

/**
 * An Interface for Generators.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IGenerator {

	/**
	 * 
	 * Creates a file formatted to display classes.
	 *
	 * @param sm
	 *            SystemModel containing all class information to diagram.
	 * @param config
	 *            IConfiguration for the Generator to follow.
	 * @param jobs
	 *            A Collection of Patterns recognized.
	 * @return the Graphvis file String
	 */
	String generate(ISystemModel sm, IConfiguration config, Iterable<IJob> jobs);

}
