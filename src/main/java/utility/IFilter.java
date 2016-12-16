package utility;

import java.util.Iterator;

public interface IFilter<T> {

    boolean filter(T data);

    default Iterable<? extends T> filter(Iterable<? extends T> in) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new FilteredIterable<T>(IFilter.this, in.iterator());
            }
        };
    }

}
