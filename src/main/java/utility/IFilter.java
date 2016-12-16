package utility;

public interface IFilter<T> {

    /**
     * TODO FRED Docs!!
     *
     * @param data TODO: FRED.
     * @return true if the data pass the test, and should be left after the
     * filter
     */
    boolean filter(T data);

    /**
     * TODO FRED Docs!
     *
     * @param iterable The iterable we want to filter
     * @return an iterable of the same type but removed all the data that do not
     * pass the test
     */
    default Iterable<? extends T> filter(Iterable<? extends T> iterable) {
        return (Iterable<T>) () -> new FilteredIterator<>(IFilter.this, iterable);
    }

}
