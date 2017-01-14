package model;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ASMParserTest {

    @Test
    public void testGetObject() {
        ClassModel a = ASMParser.getObject();
        ClassModel b = ASMParser.getObject();
        assertEquals(a, b);
    }

    @Test
    public void testGetClassByNameInnerClass() {
        ClassModel a = ASMParser.getClassByName("java.util.Map$Entry");
        assertEquals("java.util.Map$Entry", a.getName());
    }

    @Test
    public void testGetClassByNameNestedClass() {
        ClassModel a = ASMParser.getClassByName("java.lang.Math$RandomNumberGeneratorHolder");
        assertEquals("java.lang.Math$RandomNumberGeneratorHolder", a.getName());
    }

    @Test
    public void testGetClassesRecursive() {
        Set<String> expected = new HashSet<>(Arrays.asList("java.lang.String", "java.lang.Object",
                "java.lang.CharSequence", "java.lang.Comparable", "java.io.Serializable"));

        Iterable<ClassModel> ls = ASMParser.getClasses(Collections.singletonList("java.lang.String"),
                ASMParser.RECURSE_INTERFACE | ASMParser.RECURSE_SUPERCLASS);
        Set<String> actual = new HashSet<>();
        for (ClassModel c : ls)
            actual.add(c.getName());

        assertTrue("Not all interfaces get parsed", actual.containsAll(expected));

    }

    @Test
    public void testGetClassesNonRecursive() {
        Iterator<ClassModel> itr = ASMParser.getClasses(Collections.singletonList("java/lang/String"), 0).iterator();
        assertTrue(itr.hasNext());
        itr.next();
        assertFalse(itr.hasNext());
    }

    @Test
    public void testGetFieldsByNameSequence() {
        ClassModel x = ASMParser.getClassByName("java.awt.Dialog");
        assertTrue(x != null);
        FieldModel field = x.getFieldByName("modalBlocker");
        assertTrue(field != null);
    }

}
