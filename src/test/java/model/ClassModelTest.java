package model;

import labTestCI.AmazonLineParser;
import labTestCI.ILineParser;
import org.junit.Test;
import utility.IFilter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ClassModelTest {

    @Test
    public void testGetField() {
        ASMServiceProvider parser = new ASMParser();
        ClassModel model = parser.getClassByName("java.lang.String");
        assertEquals("java.lang.String", model.getName());

        Set<String> fields = new HashSet<>();
        Set<String> actfields = new HashSet<>(
                Arrays.asList("value", "hash", "serialVersionUID", "serialPersistentFields", "CASE_INSENSITIVE_ORDER"));

        model.getFields().forEach((field) -> fields.add(field.getName()));

        assertEquals(actfields, fields);
    }

    @Test
    public void testGetMethods() {
        ASMServiceProvider parser = new ASMParser();
        ClassModel model = parser.getClassByName("model.Dummy");

        Set<String> actual = new HashSet<>();
        Set<String> expected = new HashSet<>(Arrays.asList("publicMethod", "publicMethod2", "privateMethod"));

        IFilter<MethodModel> getInstanceMethod = (data) -> {
            switch (data.getMethodType()) {
                case METHOD:
                    return true;
                default:
                    return false;
            }
        };

        getInstanceMethod.filter(model.getMethods()).forEach(methodModel -> actual.add(methodModel.getName()));

        assertEquals(expected, actual);
    }

    @Test
    public void testGetInterface() {
        ASMServiceProvider parser = new ASMParser();
        ClassModel model = parser.getClassByName("java.lang.String");
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
    public void testGetStringInterfaceNonRecursive() {
        ASMClassTracker parser = ASMParser.getInstance(new IModelConfiguration() {
            @Override
            public boolean isRecursive() {
                return false;
            }

            @Override
            public Iterable<String> getClasses() {
                return Arrays.asList("java.lang.String", "java/io/Serializable", "java/lang/Comparable");
            }
        });
        ClassModel model = parser.getClassByName("java/lang/String");
        assertEquals("java.lang.String", model.getName());

        Set<String> acutalInterfaces = new HashSet<>();
        Set<String> expectInterfaces = new HashSet<>();

        expectInterfaces.add("java.io.Serializable");
        expectInterfaces.add("java.lang.Comparable");

        model.getInterfaces().forEach((interfaceModel) -> acutalInterfaces.add(interfaceModel.getName()));

        assertEquals(expectInterfaces, acutalInterfaces);
    }

    @Test
    public void testGetInterfaceLab_1_AmazonParser() {
        ASMServiceProvider parser = new ASMParser();
        String amazonQualifiedString = AmazonLineParser.class.getPackage().getName() + "." + AmazonLineParser.class.getSimpleName();
        ClassModel model = parser.getClassByName(amazonQualifiedString);
        assertEquals(amazonQualifiedString, model.getName());

        Set<String> acutalInterfaces = new HashSet<>();
        Set<String> expectInterfaces = new HashSet<>();

        String expected = ILineParser.class.getPackage().getName() + "." + ILineParser.class.getSimpleName();
        expectInterfaces.add(expected);

        model.getInterfaces().forEach((interfaceModel) -> acutalInterfaces.add(interfaceModel.getName()));

        assertEquals(expectInterfaces, acutalInterfaces);
    }

}