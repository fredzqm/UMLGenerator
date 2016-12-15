package model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import generator.IClassModel;

public class NonRecursiveASMParserTest {

	@Test
	public void asmParserString() {
		ASMServiceProvider parser = new NonRecursiveASMParser(Arrays.asList("java/lang/String"));
		ClassModel model = parser.getClassByName("java/lang/String");
		assertEquals("java.lang.String", model.getName());
		assertNull(parser.getClassByName("java.lang.Object"));
	}

	@Test
	public void asmParserStringInterface() {
		ASMServiceProvider parser = new NonRecursiveASMParser(
				Arrays.asList("java.lang.String", "java/io/Serializable", "java/lang/Comparable"));
		ClassModel model = parser.getClassByName("java/lang/String");
		assertEquals("java.lang.String", model.getName());

		Set<String> acutalInterfaces = new HashSet<>();
		Set<String> expectInterfaces = new HashSet<>();

		expectInterfaces.add("java.io.Serializable");
		expectInterfaces.add("java.lang.Comparable");

		for (IClassModel interf : model.getInterfaces())
			acutalInterfaces.add(interf.getName());

		assertEquals(expectInterfaces, acutalInterfaces);
	}

	@Test
	public void lab_1_AmazonParser() {
		ASMServiceProvider parser = new NonRecursiveASMParser(Arrays.asList("problem.AmazonLineParser", "problem.ILineParser"));
		ClassModel model = parser.getClassByName("problem/AmazonLineParser");
		assertEquals("problem.AmazonLineParser", model.getName());

		Set<String> acutalInterfaces = new HashSet<>();
		Set<String> expectInterfaces = new HashSet<>();

		expectInterfaces.add("problem.ILineParser");

		for (IClassModel interf : model.getInterfaces())
			acutalInterfaces.add(interf.getName());

		assertEquals(expectInterfaces, acutalInterfaces);
	}
}
