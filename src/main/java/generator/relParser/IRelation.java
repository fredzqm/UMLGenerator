package generator.relParser;

public interface IRelation extends Comparable<IRelation> {

	/**
	 * @return the name of class from
	 */
	String getTo();

	/**
	 * @return the name of class points to
	 */
	String getFrom();

}
