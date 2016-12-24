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

    /**
     * Returns the cardinality relationship from the to side.
     *
     * @return Cardinality relationship integer to.
     */
    int getCardinalityTo();

    /**
     * Returns the cardinality relationship from the from side.
     *
     * @return Cardinality relationship integer from.
     */
    int getCardinalityFrom();
}
