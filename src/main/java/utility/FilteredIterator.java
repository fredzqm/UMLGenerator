package utility;

import java.util.Iterator;

/**
 * A quick and dirty iterator that removed data from the iterable that do not
 * pass the test
 *
 * @param <T>
 * @author zhang
 */
class FilteredIterator<T> implements Iterator<T> {
    private Iterator<? extends T> iterator;
    private IFilter<T> filter;
    private T data;
    private boolean hasNext;
    
    /**
     * Construct a Filtered Iterator.
     *
     * @param filter
     *            the filter used to filter the iterable
     * @param iterable
     *            An iterable to iterate through. // TODO: FRED DOCS!!
     */
    FilteredIterator(IFilter<T> filter, Iterable<? extends T> iterable) {
        this.iterator = iterable.iterator();
        this.filter = filter;
        advance();
    }
    
    private void advance() {
        hasNext = false;
        while (iterator.hasNext() && !hasNext) {
            data = iterator.next();
            hasNext = filter.filter(data);
        }
    }
    
    @Override
    public boolean hasNext() {
        return hasNext;
    }
    
    @Override
    public T next() {
        T ret = data;
        advance();
        return ret;
    }
}