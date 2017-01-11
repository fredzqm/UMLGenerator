package utility;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class IMapperTest {

    @Test
    public void test() {
        Iterable<Integer> a = Arrays.asList(1, 2, 3, 4, 5, 66, 7, 87);

        IMapper<Integer, String> mapper = (i) -> "" + i;

        Iterator<String> itr = mapper.map(a).iterator();
        assertEquals("1", itr.next());
        assertEquals("2", itr.next());
        assertEquals("3", itr.next());
        assertEquals("4", itr.next());
        assertEquals("5", itr.next());
        assertEquals("66", itr.next());
        assertEquals("7", itr.next());
        assertEquals("87", itr.next());
        assertFalse(itr.hasNext());

    }

}
