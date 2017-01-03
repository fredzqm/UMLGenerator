package utility;

/**
 * IFilter is a convenient way of converting an iterable to another type
 * We only needs to implement the {@link IFilter#filter(T)} method
 *
 * @param <T>
 * @author zhang
 */
public interface IMapper<A, B> {

    /**
     * @param data
     * @return true if the data pass the test, and should be left after the
     * filter
     */
    B map(A data);

    /**
     * @param iterable The iterable we want to filter
     * @return an iterable of the same type but removed all the data that do not
     * pass the test
     */
    default Iterable<B> map(Iterable<? extends A> iterable) {
        return () -> new MappedIterator<A, B>(IMapper.this, iterable);
    }

}
