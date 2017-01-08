package utility;

public interface IExpander<A, B> {

	/**
	 * @param data
	 * @return the iterable of B that we want to expand to
	 */
	Iterable<B> expand(A data);

	/**
	 * @param iterable
	 *            The iterable we want to filter
	 * @return the aggregate iterable of all elements expanded
	 */
	default Iterable<B> expand(Iterable<? extends A> iterable) {
		return () -> new ExpandIterator<A, B>(IExpander.this, iterable);
	}
}
