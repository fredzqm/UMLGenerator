package generator.relParser;

public interface IRelation {

	/**
	 * @return the name of class from
	 */
	String getTo();

	/**
	 * @return the name of class points to
	 */
	String getFrom();

	/**
	 * Returns true if the Class has a bijective relationship.
	 *
	 * @return true if the Class has a bijective relationship.
	 */
	boolean isBijective();

}
