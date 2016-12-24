package utility;

/**
 * IFilter is a convenient way of filtering out part of unneeded elements in an iterable
 * We only needs to implement the {@link IFilter#filter(T)} method
 *
 * @param <T>
 * @author zhang
 */
public interface IFilter<T> {

    /**
     * @param data
     * @return true if the data pass the test, and should be left after the
     * filter
     */
    boolean filter(T data);

    /**
     * @param iterable The iterable we want to filter
     * @return an iterable of the same type but removed all the data that do not
     * pass the test
     */
    default Iterable<T> filter(Iterable<? extends T> iterable) {
        return (Iterable<T>) () -> new FilteredIterator<>(IFilter.this, iterable);
    }

}
