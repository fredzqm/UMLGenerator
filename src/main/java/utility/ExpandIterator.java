package utility;

import java.util.Collections;
import java.util.Iterator;

public class ExpandIterator<A, B> implements Iterator<B> {
    private IExpander<A, B> expander;
    private Iterator<? extends A> dataIterator;
    private Iterator<B> valueIterator;
    private boolean hasNext;
    private B next;

    public ExpandIterator(IExpander<A, B> iExpander, Iterable<? extends A> iterable) {
        this.expander = iExpander;
        this.dataIterator = iterable.iterator();
        this.valueIterator = Collections.emptyIterator();
        hasNext = advance();
    }

    private boolean advance() {
        while (!this.valueIterator.hasNext()) {
            if (!this.dataIterator.hasNext()) {
                return false;
            }
            A a = dataIterator.next();
            valueIterator = expander.expand(a).iterator();
        }
        next = valueIterator.next();
        return true;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public B next() {
        B ret = next;
        hasNext = advance();
        return ret;
    }
}
