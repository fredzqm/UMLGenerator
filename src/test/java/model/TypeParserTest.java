package model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TypeParserTest {

	@Test
	public void testParseClassTypeModel1() {
		String internalName = "Ljava/lang/Object";

		ClassTypeModel x = TypeParser.parseClassTypeModel(internalName);

		assertEquals(ConcreteClassTypeModel.class, x.getClass());
		assertEquals("java.lang.Object", x.getName());
		assertEquals(ASMParser.getClassByName("java.lang.Object"), x.getClassModel());
		assertEquals(0, ((ConcreteClassTypeModel) x).getGenericList().size());
	}

	@Test
	public void testParseClassTypeModel2() {
		String internalName = "Ljava/util/EventListener";

		ClassTypeModel x = TypeParser.parseClassTypeModel(internalName);

		assertEquals(ConcreteClassTypeModel.class, x.getClass());
		assertEquals("java.util.EventListener", x.getName());
		assertEquals(ASMParser.getClassByName("java.util.EventListener"), x.getClassModel());
		assertEquals(0, ((ConcreteClassTypeModel) x).getGenericList().size());
	}

	@Test
	public void testParseClassTypeModelPlaceHolder() {
		String internalName = "TE";

		ClassTypeModel x = TypeParser.parseClassTypeModel(internalName);

		assertEquals(GenericTypePlaceHolder.class, x.getClass());
		assertEquals("E", x.getName());
		assertNull(x.getClassModel());
	}

	@Test
	public void testParseClassTypeModelNested() {
		String internalName = "Ljava/lang/Comparable<TE;>";

		ClassTypeModel x = TypeParser.parseClassTypeModel(internalName);

		assertEquals(ConcreteClassTypeModel.class, x.getClass());
		assertEquals("java.lang.Comparable", x.getName());
		assertEquals(ASMParser.getClassByName("java.lang.Comparable"), x.getClassModel());
		List<ClassTypeModel> ls = ((ConcreteClassTypeModel) x).getGenericList();
		assertEquals(1, ls.size());
		ClassTypeModel y = ls.get(0);
		assertEquals(GenericTypePlaceHolder.class, y.getClass());
		assertEquals("E", y.getName());
		assertNull(y.getClassModel());
	}

	@Test
	public void testParseGenericType() {
		assertParseGenericType("T:Ljava/lang/Object", ASMParser.getClassByName("java/lang/Object"), "T");
		assertParseGenericType("P:Ljava/util/spi/LocaleServiceProvider",
				ASMParser.getClassByName("java/util/spi/LocaleServiceProvider"), "P");
		assertParseGenericType("L::Ljava/util/EventListener", ASMParser.getClassByName("java/util/EventListener"), "L");
		assertParseGenericType("T::Lsun/reflect/generics/tree/Tree",
				ASMParser.getClassByName("sun/reflect/generics/tree/Tree"), "T");
	}

	public void assertParseGenericType(String arg, ClassModel lower, String name) {
		GenericTypeModel x = TypeParser.parseGenericType(arg);
		assertEquals(lower, x.getClassModel());
		assertNull(x.getUpperBound());
		assertEquals(name, x.getName());
	}

	@Test
	public void testParseGenericList1() {
		String genericDummy = "<L::Ljava/util/EventListener;>Ljava/lang/Object;";

		List<GenericTypeModel> gls = TypeParser.parseGenericTypeList(genericDummy);
		assertEquals(1, gls.size());
		GenericTypeModel gene = gls.get(0);
		assertEquals("L", gene.getName());
		assertEquals(ASMParser.getClassByName("java.util.EventListener"), gene.getClassModel());
		assertNull(gene.getUpperBound());
	}

	@Test
	public void testParseGenericListNested() {
		String genericDummy = "<E::Ljava/lang/Comparable<TE;>;>Ljava/lang/Object;Ljava/lang/Iterable<TE;>;";

		List<GenericTypeModel> gls = TypeParser.parseGenericTypeList(genericDummy);
		assertEquals(1, gls.size());
		GenericTypeModel e = gls.get(0);
		assertEquals("E", e.getName());
		assertNull(e.getUpperBound());
		
		ClassTypeModel c = e.getLowerBound();
		assertEquals(ConcreteClassTypeModel.class, c.getClass());
		assertEquals(ASMParser.getClassByName("java.lang.Comparable"), c.getClassModel());
		
		List<ClassTypeModel> ls = ((ConcreteClassTypeModel) c).getGenericList();
		assertEquals(1, ls.size());
		ClassTypeModel ce = ls.get(0);
		assertEquals(GenericTypePlaceHolder.class, ce.getClass());
		assertEquals("E", ce.getName());
	}

}
