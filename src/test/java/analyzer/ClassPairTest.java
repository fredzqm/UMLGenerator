package analyzer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

public class ClassPairTest {

    @Test
    public void testClassPair() {
        IClassModel a = mock(IClassModel.class);
        IClassModel b = mock(IClassModel.class);
        IClassModel _a = new IClassModelFilter(a);
        IClassModel _b = new IClassModelFilter(b);

        ClassPair x = new ClassPair(a, b);
        ClassPair y = new ClassPair(_a, _b);
        assertEquals(x, y);
    }

}
