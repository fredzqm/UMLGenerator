package model;

import static org.junit.Assert.*;

import org.junit.Test;

import model.type.GenericTypeModel;

public class GenericTypeModelTest {

	@Test
	public void test() {
		testGenericTypeModel_parse("T:Ljava/lang/Object", ASMParser.getClassByName("java/lang/Object"),"T");
		testGenericTypeModel_parse("P:Ljava/util/spi/LocaleServiceProvider", ASMParser.getClassByName("java/util/spi/LocaleServiceProvider"),"P");
		testGenericTypeModel_parse("L::Ljava/util/EventListener", ASMParser.getClassByName("java/util/EventListener"),"L");
		testGenericTypeModel_parse("T::Lsun/reflect/generics/tree/Tree", ASMParser.getClassByName("sun/reflect/generics/tree/Tree"),"T");
	}

	public void testGenericTypeModel_parse(String arg, ClassModel lower, String name) {
		GenericTypeModel x = GenericTypeModel.parse(arg);
		assertEquals(lower, x.getLowerBound());
		assertNull(x.getUpperBound());
		assertEquals(name, x.getName());
	}

}
