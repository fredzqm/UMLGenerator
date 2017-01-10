package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class TypeModelTest {

    @Test
    public void testSuperTypes1() {
        TypeModel t = TypeParser.parseTypeSignature("Ljava/lang/String;");
        assertEquals("java.lang.String", t.getName());

        Iterable<TypeModel> sls = t.getSuperTypes();

        ClassModel serializable = ASMParser.getClassByName("java.io.Serializable");
        ParametizedClassModel comparable = new ParametizedClassModel(ASMParser.getClassByName("java.lang.Comparable"),
                Arrays.asList(ASMParser.getClassByName("java.lang.String")));
        ClassModel charSequence = ASMParser.getClassByName("java.lang.CharSequence");
        assertEquals(Arrays.asList(ASMParser.getObject(), serializable, comparable, charSequence), sls);
    }

    @Test
    public void testSuperTypes2() {
        TypeModel t = TypeParser.parseTypeSignature("Ljava/util/List;");
        assertEquals("java.util.List", t.getName());

        Iterable<TypeModel> sls = t.getSuperTypes();

        List<GenericTypeParam> gls = ((ClassModel) t).getGenericList();
        ParametizedClassModel collection = new ParametizedClassModel(ASMParser.getClassByName("java.util.Collection"),
                Arrays.asList(gls.get(0)));
        assertEquals(Arrays.asList(ASMParser.getObject(), collection), sls);
    }

    @Test
    public void testSuperTypes3() {
        TypeModel t = TypeParser.parseTypeSignature("Ljava/util/List<Ljava/lang/Number;>;");
        assertEquals("java.util.List", t.getName());

        Iterable<TypeModel> sls = t.getSuperTypes();
        ParametizedClassModel collection = new ParametizedClassModel(ASMParser.getClassByName("java.util.Collection"),
                Arrays.asList(ASMParser.getClassByName("java.lang.Number")));
        assertEquals(Arrays.asList(ASMParser.getObject(), collection), sls);
    }

    @Test
    public void testSuperTypes4() {
        TypeModel t = TypeParser.parseClassTypeSignature("Ljava/util/Vector<TE;>.Itr;");
        assertEquals("java.util.Vector$Itr", t.getName());

        Iterable<TypeModel> sls = t.getSuperTypes();
        ParametizedClassModel iterable = new ParametizedClassModel(ASMParser.getClassByName("java.util.Iterator"),
                Arrays.asList(new GenericTypeVarPlaceHolder("E")));
        assertEquals(Arrays.asList(ASMParser.getObject(), iterable), sls);
    }

    @Test
    public void testDependsOn0() {
        TypeModel t = TypeParser.parseTypeSignature("I");

        Collection<ClassModel> dp = t.getDependentOnClass();

        assertTrue(dp.isEmpty());
    }

    @Test
    public void testDependsOn1() {
        TypeModel t = TypeParser.parseTypeSignature("Ljava/lang/String;");
        assertEquals("java.lang.String", t.getName());

        Iterator<ClassModel> dp = t.getDependentOnClass().iterator();

        assertEquals(t, dp.next());
        assertFalse(dp.hasNext());
    }

    @Test
    public void testDependsOn2() {
        TypeModel t = TypeParser.parseTypeSignature("Ljava/util/List<Ljava/lang/Integer;>;");
        assertEquals("java.util.List", t.getName());

        Collection<ClassModel> dp = t.getDependentOnClass();
        assertEquals(2, dp.size());
        assertTrue(dp.contains(t.getClassModel()));
        assertTrue(dp.contains(ASMParser.getClassByName("java.lang.Integer")));
    }

    @Test
    public void testDependsOn3() {
        TypeModel t = TypeParser
                .parseTypeSignature("Ljava/util/Map<Ljava/lang/String;Ljava/util/List<[Ljava/lang/Double;>;>;");

        Collection<ClassModel> dp = t.getDependentOnClass();
        assertEquals(4, dp.size());
        assertTrue(dp.contains(ASMParser.getClassByName("java.util.Map")));
        assertTrue(dp.contains(ASMParser.getClassByName("java.lang.String")));
        assertTrue(dp.contains(ASMParser.getClassByName("java.util.List")));
        assertTrue(dp.contains(ASMParser.getClassByName("java.lang.Double")));
    }

    @Test
    public void testAssignTo1() {
        TypeModel t = TypeParser.parseTypeSignature("Ljava/lang/String;");
        assertEquals("java.lang.String", t.getName());

        TypeModel x = t.assignTo(ASMParser.getObject());

        assertEquals(ASMParser.getObject(), x);
    }

    @Test
    public void testAssignTo2() {
        TypeModel t = TypeParser.parseTypeSignature("Ljava/lang/String;");
        assertEquals("java.lang.String", t.getName());

        ClassModel charSequence = ASMParser.getClassByName("java.lang.CharSequence");
        TypeModel x = t.assignTo(charSequence);

        assertEquals(charSequence, x);
    }

    @Test
    public void testAssignToFail() {
        TypeModel t = TypeParser.parseTypeSignature("Ljava/lang/String;");
        assertEquals("java.lang.String", t.getName());

        ClassModel charSequence = ASMParser.getClassByName("java.lang.Integer");
        TypeModel x = t.assignTo(charSequence);
        assertNull(x);
    }

    @Test
    public void testAssignTo3() {
        TypeModel t = TypeParser.parseTypeSignature("Ljava/util/ArrayList<Ljava/lang/Integer;>;");
        assertEquals("java.util.ArrayList", t.getName());

        ClassModel list = ASMParser.getClassByName("java.util.List");
        TypeModel x = t.assignTo(list);
        ClassModel integer = ASMParser.getClassByName("java.lang.Integer");
        assertEquals(new ParametizedClassModel(list, Arrays.asList(integer)), x);

        ClassModel iterable = ASMParser.getClassByName("java.lang.Iterable");
        TypeModel y = t.assignTo(iterable);
        assertEquals(new ParametizedClassModel(iterable, Arrays.asList(integer)), y);
    }

    @Test
    public void testAssignTo4() {
        TypeModel t = TypeParser.parseTypeSignature("Ljava/util/HashMap<Ljava/lang/Integer;Ljava/lang/String;>;");
        assertEquals("java.util.HashMap", t.getName());

        ClassModel map = ASMParser.getClassByName("java.util.Map");
        TypeModel x = t.assignTo(map);
        ClassModel integer = ASMParser.getClassByName("java.lang.Integer");
        ClassModel string = ASMParser.getClassByName("java.lang.String");
        assertEquals(new ParametizedClassModel(map, Arrays.asList(integer, string)), x);
    }
}
