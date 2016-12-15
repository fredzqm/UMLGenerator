package model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import generator.IClassModel;

public class SystemModelTest {

	@Test
	public void recursiveParsing() {
		IModelConfiguration config = new IModelConfiguration() {
			@Override
			public boolean isRecursive() {
				return true;
			}

			@Override
			public Iterable<String> getClasses() {
				return Arrays.asList("javax.swing.JComponent");
			}
		};
		SystemModel sys = SystemModel.getInstance(config);

		Set<String> acutal = new HashSet<>();
		Set<String> expect = new HashSet<>(Arrays.asList("javax.swing.JComponent", "java.awt.Container",
				"java.awt.Component", "java.lang.Object"));

		for (IClassModel x : sys.getClasses())
			acutal.add(x.getName());

		assertEquals(expect, acutal);
	}

}
