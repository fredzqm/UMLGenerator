package utility;

import java.util.Iterator;

public interface IFilter<T> {

	/**
	 * 
	 * @param data
	 * @return true if the data pass the test, and should be left after the
	 *         filter
	 */
	boolean filter(T data);

	/**
	 * 
	 * @param iterable
	 *            The iterable we want to filter
	 * @return an iterable of the same type but removed all the data that do not
	 *         pass the test
	 */
	default Iterable<T> filter(Iterable<T> iterable) {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new FilteredIterator<T>(IFilter.this, iterable);
			}
		};
	}

}
