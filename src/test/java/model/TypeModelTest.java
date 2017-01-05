package model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TypeModelTest {

	@Test
	public void testSuperTypes1() {
		TypeModel t = TypeParser.parseTypeSignature("Ljava/lang/String;");
		assertEquals("java.lang.String", t.getName());

		Iterable<TypeModel> sls = t.getSuperTypes();

		ClassModel serializable = ASMParser.getClassByName("java.io.Serializable");
		ParametizedClassModel comparable = new ParametizedClassModel(ASMParser.getClassByName("java.lang.Comparable"),
				Arrays.asList(ASMParser.getClassByName("java.lang.String")));
		ClassModel charSequence = ASMParser.getClassByName("java.lang.CharSequence");
		assertEquals(Arrays.asList(ASMParser.getObject(), serializable, comparable, charSequence), sls);
	}

	@Test
	public void testSuperTypes2() {
		TypeModel t = TypeParser.parseTypeSignature("Ljava/util/List;");
		assertEquals("java.util.List", t.getName());

		Iterable<TypeModel> sls = t.getSuperTypes();

		List<GenericTypeParam> gls = ((ClassModel) t).getGenericList();
		ParametizedClassModel collection = new ParametizedClassModel(ASMParser.getClassByName("java.util.Collection"),
				Arrays.asList(gls.get(0)));
		assertEquals(Arrays.asList(ASMParser.getObject(), collection), sls);
	}

	@Test
	public void testSuperTypes3() {
		TypeModel t = TypeParser.parseTypeSignature("Ljava/util/List<Ljava/lang/Number;>;");
		assertEquals("java.util.List", t.getName());

		Iterable<TypeModel> sls = t.getSuperTypes();
		ParametizedClassModel collection = new ParametizedClassModel(ASMParser.getClassByName("java.util.Collection"),
				Arrays.asList(ASMParser.getClassByName("java.lang.Number")));
		assertEquals(Arrays.asList(ASMParser.getObject(), collection), sls);
	}

}
