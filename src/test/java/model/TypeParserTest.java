package model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import model.TypeParser.ClassSignatureParseResult;

public class TypeParserTest {

	@Test
	public void testParseClassTypeModel1() {
		String internalName = "Ljava/lang/Object;";

		TypeModel x = TypeParser.parseTypeArg(internalName);

		assertEquals("java.lang.Object", x.getName());
		assertEquals(ASMParser.getClassByName("java.lang.Object"), x.getClassModel());
	}

	@Test
	public void testParseClassTypeModel2() {
		String internalName = "Ljava/util/EventListener;";

		TypeModel x = TypeParser.parseTypeArg(internalName);

		assertEquals("java.util.EventListener", x.getName());
		assertEquals(ASMParser.getClassByName("java.util.EventListener"), x.getClassModel());
	}

	@Test
	public void testParseClassTypeModelPlaceHolder() {
		String internalName = "TE;";

		TypeModel x = TypeParser.parseTypeArg(internalName);

		assertEquals(GenericTypeVar.class, x.getClass());
		assertEquals("E", x.getName());
		assertNull(x.getClassModel());
	}

	@Test
	public void testParseClassTypeModelNested() {
		String internalName = "Ljava/lang/Comparable<TE;>;";

		TypeModel x = TypeParser.parseTypeArg(internalName);

		assertEquals(ParametizedClassModel.class, x.getClass());
		assertEquals("java.lang.Comparable", x.getName());
		assertEquals(ASMParser.getClassByName("java.lang.Comparable"), x.getClassModel());
		List<TypeModel> ls = ((ParametizedClassModel) x).getGenericList();
		assertEquals(1, ls.size());
		TypeModel y = ls.get(0);
		assertEquals(GenericTypeVar.class, y.getClass());
		assertEquals("E", y.getName());
		assertNull(y.getClassModel());
	}

	@Test
	public void testParseGenericType() {
		assertParseGenericType("T:Ljava/lang/Object;", ASMParser.getClassByName("java/lang/Object"), "T");
		assertParseGenericType("P:Ljava/util/spi/LocaleServiceProvider;",
				ASMParser.getClassByName("java/util/spi/LocaleServiceProvider"), "P");
		assertParseGenericType("L::Ljava/util/EventListener;", ASMParser.getClassByName("java/util/EventListener"), "L");
		assertParseGenericType("T::Lsun/reflect/generics/tree/Tree;",
				ASMParser.getClassByName("sun/reflect/generics/tree/Tree"), "T");
	}

	public void assertParseGenericType(String arg, ClassModel lower, String name) {
		GenericTypeParam x = TypeParser.parseTypeParam(arg);
		assertEquals(lower, x.getClassModel());
		assertEquals(name, x.getName());
	}

	@Test
	public void testParseClassSignature1() {
		String genericDummy = "<L::Ljava/util/EventListener;>Ljava/lang/Object;";

		ClassSignatureParseResult rs = TypeParser.parseClassSignature(genericDummy);
		// generated List
		List<GenericTypeParam> gls = rs.getParamsList();
		assertEquals(1, gls.size());
		GenericTypeParam gene = gls.get(0);
		assertEquals("L", gene.getName());
		assertEquals(ASMParser.getClassByName("java.util.EventListener"), gene.getClassModel());
		
		// super type list
		List<TypeModel> spls = rs.getSuperTypes();
		assertEquals(spls, Arrays.asList(new TypeModel[]{ASMParser.getObject()}));
	}

	@Test
	public void testParseClassSignatureNested() {
		String genericDummy = "<E::Ljava/lang/Comparable<TE;>;>Ljava/lang/Object;Ljava/lang/Iterable<TE;>;";

		ClassSignatureParseResult rs = TypeParser.parseClassSignature(genericDummy);
		// generated List
		List<GenericTypeParam> gls = rs.getParamsList();
		assertEquals(1, gls.size());
		GenericTypeParam te1 = gls.get(0);
		assertEquals("E", te1.getName());
		
		List<TypeModel> compls = te1.getBoundSuperTypes();
		assertEquals(1, compls.size());
		TypeModel comp2 = compls.get(0);
		assertEquals(ParametizedClassModel.class, comp2.getClass());
		assertEquals(ASMParser.getClassByName("java.lang.Comparable"), comp2.getClassModel());
		
		List<TypeModel> compPals = ((ParametizedClassModel) comp2).getGenericList();
		assertEquals(1, compPals.size());
		TypeModel compPalsTE = compPals.get(0);
		assertEquals(GenericTypeVar.class, compPalsTE.getClass());
		assertEquals("E", compPalsTE.getName());
		
		// super type list
		List<TypeModel> superTypes = rs.getSuperTypes();
		assertEquals(2, superTypes.size());
		assertEquals(ASMParser.getObject(), superTypes.get(0));
		TypeModel iter = superTypes.get(1);
		assertEquals(ASMParser.getClassByName("java.lang.Iterable"), iter.getClassModel());
		assertEquals(ParametizedClassModel.class, iter.getClass());
		List<TypeModel> iterPals = ((ParametizedClassModel)iter).getGenericList();
		assertEquals(1, iterPals.size());
		TypeModel iterPalsTE = iterPals.get(0);
		assertEquals(GenericTypeVar.class, iterPalsTE.getClass());
		assertEquals("E", iterPalsTE.getName());
	}

}
