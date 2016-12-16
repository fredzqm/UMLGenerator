package utility;

import java.util.Iterator;

class FilteredIterable<T> implements Iterator<T> {
	private Iterator<? extends T> itr;
	private IFilter<T> filter;
	private T data;

	public FilteredIterable(IFilter<T> iFilter, Iterator<? extends T> iterator) {
		itr = iterator;
		filter = iFilter;
		advance();
	}

	private void advance() {
		data = null;
		while (itr.hasNext()) {
			data = itr.next();
			if (filter.filter(data))
				break;
		}
	}

	@Override
	public boolean hasNext() {
		return itr.hasNext() && data != null;
	}

	@Override
	public T next() {
		T ret = data;
		advance();
		return ret;
	}

}