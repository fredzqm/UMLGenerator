package model;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class SignatureTest {

    @Test
    public void testTypeErasedHashCode1() {
        ClassModel x = ASMParser.getClassByName("java.util.List");
        ClassModel i = ASMParser.getClassByName("java.lang.Integer");
        Signature a = new Signature(Arrays.asList(PrimitiveType.INT, x), "set");
        Signature b = new Signature(Arrays.asList(PrimitiveType.INT, new ParametizedClassModel(x, Collections.singletonList(i))),
                "set");

        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(a, b);
    }

    @Test
    public void testTypeErasedHashCode2() {
        Signature a = new Signature(Collections.singletonList(new GenericTypeParam("E", Collections.singletonList(ASMParser.getObject()))),
                "set");
        Signature b = new Signature(Collections.singletonList(ASMParser.getObject()), "set");

        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(a, b);
    }
}
