package model;

import labTestCI.AmazonLineParser;
import labTestCI.ILineParser;
import org.junit.Test;

import utility.IFilter;
import utility.MethodType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ClassModelTest {

	@Test
	public void testGetField() {
		ClassModel model = ASMParser.getClassByName("java.lang.String");
		assertEquals("java.lang.String", model.getName());

		Set<String> fields = new HashSet<>();
		Set<String> actfields = new HashSet<>(
				Arrays.asList("value", "hash", "serialVersionUID", "serialPersistentFields", "CASE_INSENSITIVE_ORDER"));

		model.getFields().forEach((field) -> fields.add(field.getName()));

		assertEquals(actfields, fields);
	}

	@Test
	public void testGetMethods() {
		ClassModel model = ASMParser.getClassByName("dummy.Dummy");

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
		String amazonQualifiedString = AmazonLineParser.class.getPackage().getName() + "."
				+ AmazonLineParser.class.getSimpleName();
		ClassModel model = ASMParser.getClassByName(amazonQualifiedString);
		assertEquals(amazonQualifiedString, model.getName());

		Set<String> acutalInterfaces = new HashSet<>();
		Set<String> expectInterfaces = new HashSet<>();

		String expected = ILineParser.class.getPackage().getName() + "." + ILineParser.class.getSimpleName();
		expectInterfaces.add(expected);

		model.getInterfaces().forEach((interfaceModel) -> acutalInterfaces.add(interfaceModel.getName()));

		assertEquals(expectInterfaces, acutalInterfaces);
	}

}