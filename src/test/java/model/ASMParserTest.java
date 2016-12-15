package model;

import org.junit.Test;

import generator.IClassModel;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ASMParserTest {

	@Test
	public void asmParserString() {
		ASMServiceProvider parser = new ASMParser();
		ClassModel model = parser.getClassByName("java.lang.String");
		assertEquals("java.lang.String", model.getName());

		Set<String> fields = new HashSet<>();
		Set<String> actfields = new HashSet<>();

		actfields.add("value");
		actfields.add("hash");
		actfields.add("serialVersionUID");
		actfields.add("serialPersistentFields");
		actfields.add("CASE_INSENSITIVE_ORDER");

		for (FieldModel field : model.getFields())
			fields.add(field.getName());

		assertEquals(actfields, fields);
	}

	@Test
	public void asmParserStringInterface() {
		ASMServiceProvider parser = new ASMParser();
		ClassModel model = parser.getClassByName("java.lang.String");
		assertEquals("java.lang.String", model.getName());
		
		Set<String> acutalInterfaces = new HashSet<>();
		Set<String> expectInterfaces = new HashSet<>();

		expectInterfaces.add("java.io.Serializable");
		expectInterfaces.add("java.lang.Comparable");
		expectInterfaces.add("java.lang.CharSequence");

		for (IClassModel interf : model.getInterfaces())
			acutalInterfaces.add(interf.getName());

		assertEquals(expectInterfaces, acutalInterfaces);
	}

}
