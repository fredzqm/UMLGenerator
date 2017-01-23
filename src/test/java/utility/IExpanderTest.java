package utility;

import org.junit.Test;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class IExpanderTest {

    @Test
    public void test() {
        Iterable<String> a = Arrays.asList("123", "4", "", "5", "67");

        IExpander<String, Character> mapper = (i) -> new AbstractList<Character>() {
            @Override
            public Character get(int index) {
                return i.charAt(index);
            }

            @Override
            public int size() {
                return i.length();
            }
        };

        Iterator<Character> itr = mapper.expand(a).iterator();
        assertEquals('1', (char) itr.next());
        assertEquals('2', (char) itr.next());
        assertEquals('3', (char) itr.next());
        assertEquals('4', (char) itr.next());
        assertEquals('5', (char) itr.next());
        assertEquals('6', (char) itr.next());
        assertEquals('7', (char) itr.next());
    }

}
