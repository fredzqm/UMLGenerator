package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import utility.IFilter;
import utility.MethodType;
import utility.Modifier;

public class MethodModelTest {

	@Test
	public void test() {
		ASMServiceProvider parser = new ASMParser();
		ClassModel dummy = parser.getClassByName("model.Dummy");

		IFilter<MethodModel> filter = (d) -> d.getModifier() == Modifier.PUBLIC
				&& d.getMethodType() == MethodType.METHOD;

		assertEquals("model.Dummy", dummy.getName());
		Iterator<? extends MethodModel> itr = filter.filter(dummy.getMethods()).iterator();

		MethodModel methodModel = itr.next();
		assertEquals("publicMethod", methodModel.getName());
		assertFalse(itr.hasNext());

		Set<String> expected = new HashSet<>(Arrays.asList("append", "toString", "java.lang.StringBuilder"));
		Collection<String> actual = new ArrayList<>();

		Collection<MethodModel> method = methodModel.getDependentMethods();
		method.forEach((m) -> actual.add(m.getName()));
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected, new HashSet<>(actual));
	}
}
