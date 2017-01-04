package dummy;

import java.util.Iterator;

public class GenericDummyClass<E extends Comparable<E>> implements Iterable<E> {

	@Override
	public Iterator<E> iterator() {
		return null;
	}

}
