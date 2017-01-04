package model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TypeParserTest {

	@Test
	public void test() {
		testParseGenericType("T:Ljava/lang/Object", ASMParser.getClassByName("java/lang/Object"), "T");
		testParseGenericType("P:Ljava/util/spi/LocaleServiceProvider",
				ASMParser.getClassByName("java/util/spi/LocaleServiceProvider"), "P");
		testParseGenericType("L::Ljava/util/EventListener", ASMParser.getClassByName("java/util/EventListener"),
				"L");
		testParseGenericType("T::Lsun/reflect/generics/tree/Tree",
				ASMParser.getClassByName("sun/reflect/generics/tree/Tree"), "T");
	}

	public void testParseGenericType(String arg, ClassModel lower, String name) {
		GenericTypeModel x = TypeParser.parseGenericType(arg);
		assertEquals(lower, x.getClassModel());
		assertNull(x.getUpperBound());
		assertEquals(name, x.getName());
	}

	
	@Test
	public void testParseGenericListNested() {
		String genericDummy = "<E::Ljava/lang/Comparable<TE;>;>Ljava/lang/Object;Ljava/lang/Iterable<TE;>;";

		List<GenericTypeModel> gls = TypeParser.parseGenericTypeList(genericDummy);
		assertEquals(1, gls.size());
		GenericTypeModel gene = gls.get(0);
		assertEquals("E", gene.getName());
		assertEquals(ASMParser.getClassByName("java.lang.Comparable"), gene.getClassModel());
		assertNull(gene.getUpperBound());
	}
	
	
}
