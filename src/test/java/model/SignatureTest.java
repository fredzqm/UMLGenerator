package model;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class SignatureTest {

    @Test
    public void testTypeErasedHashCode1() {
        ClassModel x = ASMParser.getClassByName("java.util.List");
        ClassModel i = ASMParser.getClassByName("java.lang.Integer");
        Signature a = new Signature(Arrays.asList(PrimitiveType.INT, x), "set");
        Signature b = new Signature(Arrays.asList(PrimitiveType.INT, new ParametizedClassModel(x, Arrays.asList(i))),
                "set");

        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(a, b);
    }

    @Test
    public void testTypeErasedHashCode2() {
        ClassModel x = ASMParser.getClassByName("java.util.List");
        ClassModel i = ASMParser.getClassByName("java.util.List");
        Signature a = new Signature(Arrays.asList(new GenericTypeParam("E", Arrays.asList(ASMParser.getObject()))),
                "set");
        Signature b = new Signature(Arrays.asList(ASMParser.getObject()), "set");

        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(a, b);
    }
}
