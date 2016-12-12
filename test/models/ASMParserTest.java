package models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ASMParserTest {

	@Test
	public void test() {
		ASMServiceProvider parser = new ASMParser();
		ClassModel model =  parser.getClassByName("java.lang.String");
		assertEquals("java.lang.String", model.getName());
		System.out.println(model.getFields());
	}

}
