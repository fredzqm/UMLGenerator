package generator;

/**
 * An Interface for Generators.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IGenerator {
	
	/**
	 * * Creates a file formatted to display classes.
	 *
	 * @param config
	 * @param sm
	 *            SystemModel containing all class information to diagram and
	 *            analyze result
	 * @return the UML String.
	 */
	String generate(IGeneratorConfiguration config, ISystemModel sm);

}
