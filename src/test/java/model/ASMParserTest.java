package model;

import org.junit.Test;

import generator.IClassModel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ASMParserTest {
	
	@Test
	public void getClassesRecursive() {
		ASMParser parser = ASMParser.getInstance(new IModelConfiguration() {
			@Override
			public boolean isRecursive() {
				return true;
			}

			@Override
			public Iterable<String> getClasses() {
				return Arrays.asList("java.lang.String");
			}
		});
		Set<String> expected;
		Iterable<ClassModel> ls;
		Set<String> actual;

		expected = new HashSet<>(Arrays.asList("java.lang.String", "java.lang.Object", "java.lang.CharSequence",
				"java.lang.Comparable", "java.io.Serializable"));
		ls = parser.getClasses();
		actual = new HashSet<>();
		for (ClassModel c : ls)
			actual.add(c.getName());

		assertEquals(expected, actual);

	}
	

	@Test
	public void testGetClassesNonRecursive() {
		ASMClassTracker parser = new ASMParser();
		parser.addClasses(Arrays.asList("java/lang/String"));
		
		Iterator<ClassModel> itr = parser.getClasses().iterator();
		assertTrue(itr.hasNext());
		itr.next();
		assertFalse(itr.hasNext());
	}
}
