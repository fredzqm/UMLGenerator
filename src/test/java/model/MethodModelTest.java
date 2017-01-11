package model;

import dummy.generic.GenericDummyClass;
import dummy.hasDependsRel.Dummy;
import org.junit.Test;
import utility.IFilter;
import utility.MethodType;
import utility.Modifier;

import java.util.*;

import static org.junit.Assert.*;

public class MethodModelTest {

    @Test
    public void testGetDependentMethods() {
        String dummyClass = Dummy.class.getName();
        ClassModel dummy = ASMParser.getClassByName(dummyClass);
        assertEquals(dummyClass, dummy.getName());

        MethodModel methodModel = dummy.getMethodBySignature(Signature.parse("publicMethod", "()LString"));

        assertTrue(methodModel != null);

        Set<String> expected = new HashSet<>(Arrays.asList("append", "toString", "java.lang.StringBuilder"));
        Collection<String> actual = new ArrayList<>();

        Collection<MethodModel> methods = methodModel.getCalledMethods();
        methods.forEach((m) -> actual.add(m.getName()));

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, new HashSet<>(actual));
    }

    @Test
    public void testGetDependentFields() {
        String dummyClass = Dummy.class.getName();
        ClassModel dummy = ASMParser.getClassByName(dummyClass);

        IFilter<MethodModel> filter = (d) -> d.getModifier() == Modifier.PRIVATE
                && d.getMethodType() == MethodType.METHOD;

        assertEquals(dummyClass, dummy.getName());
        Iterator<? extends MethodModel> itr = filter.filter(dummy.getMethods()).iterator();

        MethodModel methodModel = itr.next();
        assertEquals("privateMethod", methodModel.getName());
        assertFalse(itr.hasNext());

        Set<String> expected = new HashSet<>(Arrays.asList("proctedField", "defaultField", "publicField"));
        Collection<String> actual = new ArrayList<>();

        Collection<FieldModel> method = methodModel.getAccessedFields();
        method.forEach((m) -> actual.add(m.getName()));

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, new HashSet<>(actual));
    }


    @Test
    public void testGetMethodType1() {
        String dummyClass = GenericDummyClass.class.getName();
        ClassModel genericdummy = ASMParser.getClassByName(dummyClass);

        MethodModel iteratorMethod = genericdummy.getMethodBySignature(new Signature(Arrays.asList(), "iterator"));

        assertEquals("iterator", iteratorMethod.getName());
        TypeModel ret = iteratorMethod.getReturnType();
        assertEquals(ASMParser.getClassByName("java.util.Iterator"), ret.getClassModel());
        assertEquals(genericdummy.getGenericList().get(0), ret.getGenericArg(0));
    }
}
