package model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import model.TypeParser.ClassSignatureParseResult;

public class TypeParserTest {

	@Test
	public void testParseClassTypeModel1() {
		String internalName = "Ljava/lang/Object";

		ClassTypeModel x = TypeParser.parseClassTypeModel(internalName);

		assertEquals("java.lang.Object", x.getName());
		assertEquals(ASMParser.getClassByName("java.lang.Object"), x.getClassModel());
	}

	@Test
	public void testParseClassTypeModel2() {
		String internalName = "Ljava/util/EventListener";

		ClassTypeModel x = TypeParser.parseClassTypeModel(internalName);

		assertEquals("java.util.EventListener", x.getName());
		assertEquals(ASMParser.getClassByName("java.util.EventListener"), x.getClassModel());
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

		assertEquals(ParametizedClassModel.class, x.getClass());
		assertEquals("java.lang.Comparable", x.getName());
		assertEquals(ASMParser.getClassByName("java.lang.Comparable"), x.getClassModel());
		List<ClassTypeModel> ls = ((ParametizedClassModel) x).getGenericList();
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
	public void testParseClassSignature1() {
		String genericDummy = "<L::Ljava/util/EventListener;>Ljava/lang/Object;";

		ClassSignatureParseResult rs = TypeParser.parseClassSignature(genericDummy);
		// generated List
		List<GenericTypeModel> gls = rs.getGenericList();
		assertEquals(1, gls.size());
		GenericTypeModel gene = gls.get(0);
		assertEquals("L", gene.getName());
		assertEquals(ASMParser.getClassByName("java.util.EventListener"), gene.getClassModel());
		assertNull(gene.getUpperBound());
		
		// super type list
		List<ClassTypeModel> spls = rs.getSuperTypes();
		assertEquals(spls, Arrays.asList(new ClassTypeModel[]{ASMParser.getObject()}));
	}

	@Test
	public void testParseClassSignatureNested() {
		String genericDummy = "<E::Ljava/lang/Comparable<TE;>;>Ljava/lang/Object;Ljava/lang/Iterable<TE;>;";

		ClassSignatureParseResult rs = TypeParser.parseClassSignature(genericDummy);
		// generated List
		List<GenericTypeModel> gls = rs.getGenericList();
		assertEquals(1, gls.size());
		GenericTypeModel te1 = gls.get(0);
		assertEquals("E", te1.getName());
		assertNull(te1.getUpperBound());
		
		ClassTypeModel comp = te1.getLowerBound();
		assertEquals(ParametizedClassModel.class, comp.getClass());
		assertEquals(ASMParser.getClassByName("java.lang.Comparable"), comp.getClassModel());
		
		List<ClassTypeModel> compPals = ((ParametizedClassModel) comp).getGenericList();
		assertEquals(1, compPals.size());
		ClassTypeModel compPalsTE = compPals.get(0);
		assertEquals(GenericTypePlaceHolder.class, compPalsTE.getClass());
		assertEquals("E", compPalsTE.getName());
		
		// super type list
		List<ClassTypeModel> superTypes = rs.getSuperTypes();
		assertEquals(2, superTypes.size());
		assertEquals(ASMParser.getObject(), superTypes.get(0));
		ClassTypeModel iter = superTypes.get(1);
		assertEquals(ASMParser.getClassByName("java.lang.Iterable"), iter.getClassModel());
		assertEquals(ParametizedClassModel.class, iter.getClass());
		List<ClassTypeModel> iterPals = ((ParametizedClassModel)iter).getGenericList();
		assertEquals(1, iterPals.size());
		ClassTypeModel iterPalsTE = iterPals.get(0);
		assertEquals(GenericTypePlaceHolder.class, iterPalsTE.getClass());
		assertEquals("E", iterPalsTE.getName());
	}

}
