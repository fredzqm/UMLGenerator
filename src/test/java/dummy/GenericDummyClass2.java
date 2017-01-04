package dummy;

import java.util.Iterator;
import java.util.Map;

public class GenericDummyClass2<A, E extends Map<A, A>> implements Iterable<E> {

	@Override
	public Iterator<E> iterator() {
		return null;
	}

}
