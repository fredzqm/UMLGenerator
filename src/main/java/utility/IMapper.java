package utility;

/**
 * IFilter is a convenient way of converting an iterable to another type We only
 * needs to implement the {@link IFilter#filter(T)} method
 *
 * @param <T>
 * @author zhang
 */
public interface IMapper<A, B> {
	/**
	 * @param data
	 * @return the corresponding element of B that it maps to
	 */
	B map(A data);

	/**
	 * @param iterable
	 *            The iterable we want to map
	 * @return an iterable of type B mapped to
	 */
	default Iterable<B> map(Iterable<? extends A> iterable) {
		return () -> new MappedIterator<A, B>(IMapper.this, iterable);
	}
}
