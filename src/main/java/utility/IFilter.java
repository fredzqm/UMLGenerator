package utility;

import java.util.Iterator;

public interface IFilter<T> {

    boolean filter(T data);

    default Iterable<T> filter(Iterable<T> in) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new FilteredIterable<T>(IFilter.this, in.iterator());
            }
        };
    }

    static class FilteredIterable<T> implements Iterator<T> {
        private Iterator<T> itr;
        private IFilter<T> filter;
        private T data;

        public FilteredIterable(IFilter<T> iFilter, Iterator<T> originIterator) {
            itr = originIterator;
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
}
