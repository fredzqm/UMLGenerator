package test.java.model;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import main.java.model.ASMParser;
import main.java.model.ASMServiceProvider;
import main.java.model.ClassModel;
import main.java.model.FieldModel;


public class ASMParserTest {

	@Test
	public void test() {
		ASMServiceProvider parser = new ASMParser();
		ClassModel model =  parser.getClassByName("java.lang.String");
		assertEquals("java.lang.String", model.getName());
		
		Set<String> fields = new HashSet<>();
		Set<String> actfields = new HashSet<>();
		
		actfields.add("value");
		actfields.add("hash");
		actfields.add("serialVersionUID");
		actfields.add("serialPersistentFields");
		actfields.add("CASE_INSENSITIVE_ORDER");

		
		for(FieldModel field: model.getFields())
			fields.add(field.getName());
		
		
		assertEquals(actfields, fields);
	}

}
