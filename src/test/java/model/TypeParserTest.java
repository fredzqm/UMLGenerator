package model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import model.TypeParser.ClassSignatureParseResult;

public class TypeParserTest {

	@Test
	public void testParseTypeSigPrimitive() {
		String name = "Z";
		TypeModel x = TypeParser.parseTypeSignature(name);
		assertEquals(PrimitiveType.BOOLEAN, x);
	}

	@Test
	public void testParseTypeSigList1() {
		String name = "Ljava/util/List<TE;>;";
		TypeModel x = TypeParser.parseTypeSignature(name);
		assertEquals(ParametizedClassModel.class, x.getClass());
		assertEquals(ASMParser.getClassByName("java.util.List"), x.getClassModel());
		assertEquals(Arrays.asList(new TypeModel[] { new GenericTypeVarPlaceHolder("E") }),
				((ParametizedClassModel) x).getGenericArgs());
	}

	@Test(expected = RuntimeException.class)
	public void testParseFieldTypeSigPrimitive() {
		String name = "Z";
		TypeParser.parseFieldTypeSignature(name);
	}

	@Test
	public void testParseFieldTypeIntArray() {
		String name = "[[I";
		TypeModel x = TypeParser.parseTypeSignature(name);
		assertEquals(2, x.getDimension());
		assertNull(x.getClassModel());
	}

	@Test
	public void testParseClassTypeArg1() {
		String name = "Ljava/lang/Object;";

		TypeModel x = TypeParser.parseTypeArg(name);

		assertEquals("java.lang.Object", x.getName());
		assertEquals(ASMParser.getClassByName("java.lang.Object"), x.getClassModel());
	}

	@Test
	public void testParseClassTypeArg2() {
		String name = "Ljava/util/EventListener;";

		TypeModel x = TypeParser.parseTypeArg(name);

		assertEquals("java.util.EventListener", x.getName());
		assertEquals(ASMParser.getClassByName("java.util.EventListener"), x.getClassModel());
	}

	@Test
	public void testParseClassTypeArgPlaceHolder() {
		String name = "TE;";

		TypeModel x = TypeParser.parseTypeArg(name);

		assertEquals(GenericTypeVarPlaceHolder.class, x.getClass());
		assertEquals("E", x.getName());
		assertNull(x.getClassModel());
	}

	@Test
	public void testParseClassTypeArgVariable() {
		String name = "Ljava/lang/Comparable<TE;>;";

		TypeModel x = TypeParser.parseTypeArg(name);

		assertEquals(ParametizedClassModel.class, x.getClass());
		assertEquals("java.lang.Comparable", x.getName());
		assertEquals(ASMParser.getClassByName("java.lang.Comparable"), x.getClassModel());
		List<TypeModel> args = ((ParametizedClassModel) x).getGenericArgs();
		assertEquals(1, args.size());
		TypeModel y = args.get(0);
		assertEquals(GenericTypeVarPlaceHolder.class, y.getClass());
		assertEquals("E", y.getName());
		assertNull(y.getClassModel());
	}

	@Test
	public void testParseClassTypeParam2() {
		String name = "Ljava/util/EventListener;";

		TypeModel x = TypeParser.parseTypeArg(name);

		assertEquals("java.util.EventListener", x.getName());
		assertEquals(ASMParser.getClassByName("java.util.EventListener"), x.getClassModel());
	}

	@Test
	public void testParseClassTypeParamList() {
		String name = "<K::Ljava/util/EventListener;V::Ljava/lang/Comparable<TV;>;>";

		List<GenericTypeParam> ls = TypeParser.parseTypeParams(name);
		assertEquals(2, ls.size());
		GenericTypeParam x = ls.get(0);
		assertEquals("K", x.getName());
		assertEquals(ASMParser.getClassByName("java.util.EventListener"), x.getClassModel());
		GenericTypeParam y = ls.get(1);
		assertEquals("V", y.getName());
		assertEquals(ASMParser.getClassByName("java.lang.Comparable"), y.getClassModel());
		TypeModel comp = y.getBoundSuperTypes().get(0);
		assertEquals(ParametizedClassModel.class, comp.getClass());
		assertEquals("java.lang.Comparable", comp.getName());
		List<TypeModel> compArgLs = ((ParametizedClassModel) comp).getGenericArgs();
		assertEquals(1, compArgLs.size());
		TypeModel compTV = compArgLs.get(0);
		assertEquals(GenericTypeVarPlaceHolder.class, compTV.getClass());
		assertEquals("V", compTV.getName());
	}

	@Test
	public void testParseGenericType() {
		assertParseGenericType("T:Ljava/lang/Object;", ASMParser.getClassByName("java/lang/Object"), "T");
		assertParseGenericType("P:Ljava/util/spi/LocaleServiceProvider;",
				ASMParser.getClassByName("java/util/spi/LocaleServiceProvider"), "P");
		assertParseGenericType("L::Ljava/util/EventListener;", ASMParser.getClassByName("java/util/EventListener"),
				"L");
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
		assertEquals(spls, Arrays.asList(new TypeModel[] { ASMParser.getObject() }));
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

		List<TypeModel> compArgss = ((ParametizedClassModel) comp2).getGenericArgs();
		assertEquals(1, compArgss.size());
		TypeModel compPalsTE = compArgss.get(0);
		assertEquals(GenericTypeVarPlaceHolder.class, compPalsTE.getClass());
		assertEquals("E", compPalsTE.getName());

		// super type list
		List<TypeModel> superTypes = rs.getSuperTypes();
		assertEquals(2, superTypes.size());
		assertEquals(ASMParser.getObject(), superTypes.get(0));
		TypeModel iter = superTypes.get(1);
		assertEquals(ASMParser.getClassByName("java.lang.Iterable"), iter.getClassModel());
		assertEquals(ParametizedClassModel.class, iter.getClass());
		List<TypeModel> iterArgs = ((ParametizedClassModel) iter).getGenericArgs();
		assertEquals(1, iterArgs.size());
		TypeModel iterPalsTE = iterArgs.get(0);
		assertEquals(GenericTypeVarPlaceHolder.class, iterPalsTE.getClass());
		assertEquals("E", iterPalsTE.getName());
	}

}
