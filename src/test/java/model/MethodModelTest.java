package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import dummy.generic.GenericDummyClass;
import dummy.hasDependsRel.Dummy;
import utility.IFilter;
import utility.MethodType;
import utility.Modifier;

public class MethodModelTest {

    @Test
    public void testGetDependentMethods() {
        String dummyClass = Dummy.class.getName();
        ClassModel dummy = ASMParser.getClassByName(dummyClass);
        assertEquals(dummyClass, dummy.getName());

        MethodModel methodModel = dummy.getMethodBySignature(Signature.parse("publicMethod", "()LString"));

        assertTrue(methodModel != null);

        Set<String> expected = new HashSet<>(Arrays.asList("append", "toString", "java.lang.StringBuilder"));
        Collection<String> actual = new HashSet<>();

        Collection<InstructionModel> instructions = methodModel.getInstructions();
        instructions.forEach((m) -> {
            if (m instanceof InstructionMethod) {
                InstructionMethod minsn = InstructionMethod.class.cast(m);
                if (minsn != null)
                    actual.add(minsn.getMethod().getName());
            }
        });

        assertEquals(expected, actual);
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
        Collection<String> actual = new HashSet<>();

        Collection<InstructionModel> method = methodModel.getInstructions();
        method.forEach((m) -> {
            if (m instanceof InstructionField) {
                InstructionField minsn = InstructionField.class.cast(m);
                actual.add(minsn.getField().getName());
            }
        });

        assertEquals(expected, actual);
    }

    @Test
    public void testGetMethodBySignature1() {
        String dummyClass = GenericDummyClass.class.getName();
        ClassModel genericdummy = ASMParser.getClassByName(dummyClass);

        MethodModel iteratorMethod = genericdummy.getMethodBySignature(new Signature(Arrays.asList(), "iterator"));

        assertEquals("iterator", iteratorMethod.getName());
        assertEquals(genericdummy, iteratorMethod.getBelongTo());
        TypeModel ret = iteratorMethod.getReturnType();
        assertEquals(ASMParser.getClassByName("java.util.Iterator"), ret.getClassModel());
        assertEquals(genericdummy.getGenericList().get(0), ret.getGenericArg(0));
    }

    @Test
    public void testGetMethodBySignature2() {
        String string = String.class.getName();
        ClassModel stringClass = ASMParser.getClassByName(string);

        MethodModel constructor = stringClass.getMethodBySignature(new Signature(Arrays.asList(), "<init>"));
        assertEquals("java.lang.String", constructor.getName());
        assertEquals(stringClass, constructor.getBelongTo());
        assertEquals(PrimitiveType.VOID, constructor.getReturnType());

        MethodModel chars = stringClass.getMethodBySignature(new Signature(Arrays.asList(), "chars"));
        assertEquals("chars", chars.getName());
        assertEquals(ASMParser.getClassByName("java.lang.CharSequence"), chars.getBelongTo());
        assertEquals(ASMParser.getClassByName("java.util.stream.IntStream"), chars.getReturnType());
    }

    @Test
    public void testGetMethodBySignature3() {
        ClassModel throwable = ASMParser.getClassByName("java/lang/Throwable");

        MethodModel fillInStackTrace = throwable
                .getMethodBySignature(new Signature(Arrays.asList(), "fillInStackTrace"));
        assertEquals("fillInStackTrace", fillInStackTrace.getName());
        assertEquals(throwable, fillInStackTrace.getBelongTo());
        assertEquals(throwable, fillInStackTrace.getReturnType());
    }

    @Test
    public void testGetMethodBySignature4() {
        ClassModel clazz = ASMParser.getClassByName("java.security.AccessController");

        List<TypeModel> argsLs = Arrays.asList(ASMParser.getClassByName("java.security.PrivilegedAction"));
        MethodModel method = clazz.getMethodBySignature(new Signature(argsLs, "doPrivileged"));
        assertEquals("doPrivileged", method.getName());
        assertEquals(clazz, method.getBelongTo());
        assertEquals(method.getGenericList().get(0), method.getReturnType());
    }
}
