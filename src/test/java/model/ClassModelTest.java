package model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.easymock.*;

public class ClassModelTest {

	@Test
	public void classModelTest() {
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

		ClassModel stringModel = parser.getClassByName("java.lang.String");
		expected = new HashSet<>(Arrays.asList("java.io.Serializable", "java.lang.CharSequence", "java.lang.Comparable"));
		ls = stringModel.getInterfaces();
		actual = new HashSet<>();
		for (ClassModel c : ls)
			actual.add(c.getName());

		assertEquals(expected, actual);
	}

}
