package dummy;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class GenericDummyClass2<A, E extends Map<A, A>> extends Observable implements Iterable<E> {
    A a;
    E[] arrayE;
    List<A> listA;
    Map<A, E> mapAtoE;

    @Override
    public Iterator<E> iterator() {
        return null;
    }

}
