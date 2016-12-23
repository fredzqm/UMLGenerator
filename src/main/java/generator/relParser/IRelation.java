package generator.relParser;

public interface IRelation {

	/**
	 * 
	 * @return the name of class from
	 */
	String getToName();

	/**
	 * 
	 * @return the name of class points to
	 */
	String getFromName();

}
