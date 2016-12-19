package model;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

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
				return Collections.singletonList("java.lang.String");
			}
		});
		Set<String> expected;
		Iterable<ClassModel> ls;
		Set<String> actual;

		expected = new HashSet<>(Arrays.asList("java.lang.String", "java.lang.Object", "java.lang.CharSequence",
				"java.lang.Comparable", "java.io.Serializable"));
		ls = parser.freezeClassCreation();
		actual = new HashSet<>();
		for (ClassModel c : ls)
			actual.add(c.getName());

		assertTrue("Not all interfaces get parsed", actual.containsAll(expected));
		
	}

	@Test
	public void testGetClassesNonRecursive() {
		ASMClassTracker parser = new ASMParser();
		parser.addClasses(Collections.singletonList("java/lang/String"));

		Iterator<ClassModel> itr = parser.freezeClassCreation().iterator();
		assertTrue(itr.hasNext());
		itr.next();
		assertFalse(itr.hasNext());
	}
	
	@Test
	public void testGetFieldsByNameSequence() {
		ASMParser parser = ASMParser.getInstance(new IModelConfiguration() {
			@Override
			public boolean isRecursive() {
				return true;
			}
			@Override
			public Iterable<String> getClasses() {
				return Arrays.asList("java.awt.Window", "java.awt.Dialog");
			}
		});
		parser.freezeClassCreation();
		ClassModel x = parser.getClassByName("java.awt.Dialog");
		assertTrue(x != null);
		FieldModel field = x.getFieldByName("modalBlocker");
		assertTrue(field != null);
	}
}
