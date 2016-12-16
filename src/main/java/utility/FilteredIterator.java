package utility;

import java.util.Iterator;

/**
 * A quick and dirty iterator that removed data from the iterable that do not
 * pass the test
 * 
 * @author zhang
 *
 * @param <T>
 */
class FilteredIterator<T> implements Iterator<T> {
	private Iterator<? extends T> iterator;
	private IFilter<T> filter;
	private T data;

	/**
	 * create a Filtered Iterator
	 * 
	 * @param filter
	 *            the filter used to filter the iterable
	 * @param iterable
	 */
	public FilteredIterator(IFilter<T> filter, Iterable<? extends T> iterable) {
		this.iterator = iterable.iterator();
		this.filter = filter;
		advance();
	}

	private void advance() {
		data = null;
		while (iterator.hasNext()) {
			data = iterator.next();
			if (filter.filter(data))
				break;
		}
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext() && data != null;
	}

	@Override
	public T next() {
		T ret = data;
		advance();
		return ret;
	}

}