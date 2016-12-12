package test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import models.ASMParser;
import models.ASMServiceProvider;
import models.ClassModel;
import models.FieldModel;

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
