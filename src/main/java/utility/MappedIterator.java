package utility;

import java.util.Iterator;

class MappedIterator<A, B> implements Iterator<B> {

    private Iterator<? extends A> itr;
    private IMapper<A, B> mapper;

    public MappedIterator(IMapper<A, B> iMapper, Iterable<? extends A> iterable) {
        this.itr = iterable.iterator();
        this.mapper = iMapper;
    }

    @Override
    public boolean hasNext() {
        return itr.hasNext();
    }

    @Override
    public B next() {
        return mapper.map(itr.next());
    }

}
