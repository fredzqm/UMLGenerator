package util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

public class IFilterTest {

	@Test
	public void test() {
		Iterable<Integer> a = Arrays.asList(1, 2, 3, 4, 5, 66, 7, 87);

		IFilter<Integer> f = new IFilter<Integer>() {
			@Override
			public boolean filter(Integer data) {
				return data % 2 != 0;
			}

		};
		Iterator<Integer> itr = f.filter(a).iterator();
		assertEquals(1, (int) itr.next());
		assertEquals(3, (int) itr.next());
		assertEquals(5, (int) itr.next());
		assertEquals(7, (int) itr.next());
		assertEquals(87, (int) itr.next());
		assertFalse(itr.hasNext());
	}

}
