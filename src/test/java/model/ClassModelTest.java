package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import dummy.generic.GenericDummyClass;
import dummy.generic.GenericDummyClass2;
import dummy.hasDependsRel.Dummy;
import labTestCI.AmazonLineParser;
import labTestCI.ILineParser;
import utility.ClassType;
import utility.IFilter;
import utility.MethodType;
import utility.Modifier;

public class ClassModelTest {

    @Test
    public void testGetBasicProperties1() {
        ClassModel model = ASMParser.getClassByName("java.lang.String");
        assertEquals("java.lang.String", model.getName());
        assertEquals(Modifier.PUBLIC, model.getModifier());
        assertEquals(ClassType.CONCRETE, model.getType());
        assertTrue(model.isFinal());
        assertTrue(model.isStatic());
    }

    @Test
    public void testGetBasicProperties2() {
        ClassModel model = ASMParser.getClassByName("java.util.Map$Entry");
        assertEquals("java.util.Map$Entry", model.getName());
        assertEquals(Modifier.PUBLIC, model.getModifier());
        assertEquals(ClassType.INTERFACE, model.getType());
        assertFalse(model.isFinal());
        assertTrue(model.isStatic());
    }

    @Test
    public void testGetBasicProperties3() {
        ClassModel model = ASMParser.getClassByName("java.util.LinkedList$ListItr");
        assertEquals("java.util.LinkedList$ListItr", model.getName());
        assertEquals(Modifier.PRIVATE, model.getModifier());
        assertEquals(ClassType.CONCRETE, model.getType());
        assertFalse(model.isFinal());
        assertFalse(model.isStatic());
    }

    @Test
    public void testGetBasicProperties4() {
        ClassModel model = ASMParser.getClassByName("java.util.LinkedList$Node");
        assertEquals("java.util.LinkedList$Node", model.getName());
        assertEquals(Modifier.PRIVATE, model.getModifier());
        assertEquals(ClassType.CONCRETE, model.getType());
        assertFalse(model.isFinal());
        assertTrue(model.isStatic());
    }

    @Test
    public void testGetField() {
        ClassModel model = ASMParser.getClassByName("java.lang.String");
        assertEquals("java.lang.String", model.getName());
        assertEquals(Modifier.PUBLIC, model.getModifier());

        Set<String> fields = new HashSet<>();
        Set<String> actfields = new HashSet<>(
                Arrays.asList("value", "hash", "serialVersionUID", "serialPersistentFields", "CASE_INSENSITIVE_ORDER"));

        model.getFields().forEach((field) -> fields.add(field.getName()));

        assertEquals(actfields, fields);
    }

    @Test
    public void testGetMethods() {
        String dummy = Dummy.class.getName();
        ClassModel model = ASMParser.getClassByName(dummy);

        Set<String> actual = new HashSet<>();
        Set<String> expected = new HashSet<>(Arrays.asList("publicMethod", "privateMethod"));

        IFilter<MethodModel> getInstanceMethod = (d) -> d.getMethodType() == MethodType.METHOD;

        getInstanceMethod.filter(model.getMethods()).forEach(methodModel -> actual.add(methodModel.getName()));

        assertEquals(expected, actual);
    }

    @Test
    public void testGetInterface() {
        ClassModel model = ASMParser.getClassByName("java.lang.String");
        assertEquals("java.lang.String", model.getName());

        Set<String> acutalInterfaces = new HashSet<>();
        Set<String> expectInterfaces = new HashSet<>();

        expectInterfaces.add("java.io.Serializable");
        expectInterfaces.add("java.lang.Comparable");
        expectInterfaces.add("java.lang.CharSequence");

        model.getInterfaces().forEach((interfaceModel) -> acutalInterfaces.add(interfaceModel.getName()));

        assertEquals(expectInterfaces, acutalInterfaces);
    }

    @Test
    public void testGetInterfaceLab_1_AmazonParser() {
        String amazonQualifiedString = AmazonLineParser.class.getName();
        ClassModel model = ASMParser.getClassByName(amazonQualifiedString);
        assertEquals(amazonQualifiedString, model.getName());

        Set<String> acutalInterfaces = new HashSet<>();
        Set<String> expectInterfaces = new HashSet<>();

        String expected = ILineParser.class.getPackage().getName() + "." + ILineParser.class.getSimpleName();
        expectInterfaces.add(expected);

        model.getInterfaces().forEach((interfaceModel) -> acutalInterfaces.add(interfaceModel.getName()));

        assertEquals(expectInterfaces, acutalInterfaces);
    }

    @Test
    public void testGetGenericNonGeneric() {
        String dummy = Dummy.class.getName();
        ClassModel model = ASMParser.getClassByName(dummy);
        assertEquals(dummy, model.getName());

        List<GenericTypeParam> gls = model.getGenericList();
        assertEquals(0, gls.size());
    }

    @Test
    public void testGetGeneric() {
        String genericDummy = GenericDummyClass.class.getPackage().getName() + "."
                + GenericDummyClass.class.getSimpleName();
        ClassModel model = ASMParser.getClassByName(genericDummy);
        assertEquals(genericDummy, model.getName());

        List<GenericTypeParam> gls = model.getGenericList();
        assertEquals(1, gls.size());
        GenericTypeParam gene = gls.get(0);
        assertEquals("E", gene.getName());
        assertEquals(ASMParser.getObject(), gene.getClassModel());
    }

    @Test
    public void testGetGeneric2() {
        String genericDummy = GenericDummyClass2.class.getPackage().getName() + "."
                + GenericDummyClass2.class.getSimpleName();
        ClassModel model = ASMParser.getClassByName(genericDummy);
        assertEquals(genericDummy, model.getName());

        // generic list
        List<GenericTypeParam> gls = model.getGenericList();
        assertEquals(2, gls.size());
        GenericTypeParam gene1A = gls.get(0);
        assertEquals("A", gene1A.getName());
        assertEquals(ASMParser.getObject(), gene1A.getClassModel());

        GenericTypeParam gene2E = gls.get(1);
        assertEquals("E", gene2E.getName());
        TypeModel gene2Bound1 = gene2E.getBoundSuperTypes().get(0);
        assertEquals(ParametizedClassModel.class, gene2Bound1.getClass());
        assertEquals(ASMParser.getClassByName("java.util.Map"), gene2Bound1.getClassModel());
        List<TypeModel> gene2Bound1Args = ((ParametizedClassModel) gene2Bound1).getGenericArgs();
        assertEquals(Arrays.asList(gene1A, gene1A), gene2Bound1Args);

        // super types
        List<TypeModel> superTypeLs = model.getSuperTypes();
        assertEquals(2, superTypeLs.size());
        TypeModel superType1 = superTypeLs.get(0);
        assertEquals(ClassModel.class, superType1.getClass());
        assertEquals(ASMParser.getClassByName("java.util.Observable"), superType1.getClassModel());

        TypeModel superType2 = superTypeLs.get(1);
        assertEquals(ParametizedClassModel.class, superType2.getClass());
        assertEquals(ASMParser.getClassByName("java.lang.Iterable"), superType2.getClassModel());
        List<TypeModel> superType2Argss = ((ParametizedClassModel) superType2).getGenericArgs();
        assertEquals(Collections.singletonList(gene2E), superType2Argss);

        // fields
        TypeModel aType = model.getFieldByName("a").getFieldType();
        assertEquals(gene1A, aType);
        TypeModel arrayEType = model.getFieldByName("arrayE").getFieldType();
        assertEquals(new ArrayTypeModel(gene2E, 1), arrayEType);
        TypeModel listAType = model.getFieldByName("listA").getFieldType();
        assertEquals(new ParametizedClassModel(ASMParser.getClassByName("java.util.List"), Collections.singletonList(gene1A)),
                listAType);
        TypeModel mapAtoEType = model.getFieldByName("mapAtoE").getFieldType();
        assertEquals(
                new ParametizedClassModel(ASMParser.getClassByName("java.util.Map"), Arrays.asList(gene1A, gene2E)),
                mapAtoEType);

    }

}