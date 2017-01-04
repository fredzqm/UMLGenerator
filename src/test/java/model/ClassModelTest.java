package model;

import labTestCI.AmazonLineParser;
import labTestCI.ILineParser;

import org.junit.Test;

import dummy.Dummy;
import dummy.GenericDummyClass;
import dummy.GenericDummyClass2;
import utility.IFilter;
import utility.MethodType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

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

	@Test
	public void testGetGenericNonGeneric() {
		String dummy = Dummy.class.getPackage().getName() + "." + Dummy.class.getSimpleName();
		ClassModel model = ASMParser.getClassByName(dummy);
		assertEquals(dummy, model.getName());

		List<GenericTypeModel> gls = model.getGenericList();
		assertEquals(0, gls.size());
	}
	
	@Test
	public void testGetGeneric() {
		String genericDummy = GenericDummyClass.class.getPackage().getName() + "."
				+ GenericDummyClass.class.getSimpleName();
		ClassModel model = ASMParser.getClassByName(genericDummy);
		assertEquals(genericDummy, model.getName());

		List<GenericTypeModel> gls = model.getGenericList();
		assertEquals(1, gls.size());
		GenericTypeModel gene = gls.get(0);
		assertEquals("E", gene.getName());
		assertEquals(ASMParser.getObject(), gene.getClassModel());
		assertNull(gene.getUpperBound());
	}
	
	@Test
	public void testGetGeneric2() {
		String genericDummy = GenericDummyClass2.class.getPackage().getName() + "."
				+ GenericDummyClass2.class.getSimpleName();
		ClassModel model = ASMParser.getClassByName(genericDummy);
		assertEquals(genericDummy, model.getName());
		
		List<GenericTypeModel> gls = model.getGenericList();
		assertEquals(2, gls.size());
		GenericTypeModel gene1 = gls.get(0);
		assertEquals("A", gene1.getName());
		assertEquals(ASMParser.getObject(), gene1.getClassModel());
		assertNull(gene1.getUpperBound());
		
		GenericTypeModel gene2 = gls.get(1);
		assertEquals("E", gene2.getName());
		assertEquals(ASMParser.getClassByName("java.util.Map"), gene2.getClassModel());
		assertNull(gene2.getUpperBound());
	}
}