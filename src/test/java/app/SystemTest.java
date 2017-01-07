package app;
import config.Configuration;
import dummy.GenDummyClass;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import analyzer.IASystemModel;
import app.AbstractUMLEngine;
import app.UMLEngine;
import utility.Modifier;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * The GraphVizGenerator and GraphVizRunner Test.
 * <p>
 * Created by lamd on 12/11/2016.
 */
public class SystemTest {
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	private String dummyClassName = GenDummyClass.class.getPackage().getName() + "."
			+ GenDummyClass.class.getSimpleName();

	@Test
	public void graphVizGenerate() throws IOException {
		// Set up the config.
		Configuration config = Configuration.getInstance();
		config.setClasses(Arrays.asList(dummyClassName));
		config.setRecursive(true);
		config.setNodesep(1.0);
		config.setRankDir("BT");

		AbstractUMLEngine engine = UMLEngine.getInstance(config);
		IASystemModel systemModel = engine.createSystemModel();
		systemModel = engine.analyze(systemModel);
		String actual = engine.generate(systemModel);

		// Test if it has the basic DOT file styling.
		assertTrue("Missing nodesep.", actual.contains("nodesep=1.0;"));
		assertTrue("Missing node shape.", actual.contains("node [shape=record];"));
		assertTrue("Missing rankdir.", actual.contains("rankdir=BT"));
		assertTrue("Missing primary class name.", actual.contains(String.format("\"%s\"", dummyClassName)));

		// See if it has its expected super class.
		String expectedSuperClass = String.format("\"%s\" -> \"java.lang.Object\" [arrowhead=\"onormal\" style=\"\" ];",
				dummyClassName);
		assertTrue("Missing super class relation.", actual.contains(expectedSuperClass));

		// See if it has its expected dependencies.
		String expectedDependencies = String.format(
				"\"%s\" -> \"java.lang.String\" [arrowhead=\"vee\" style=\"\" taillabel=\"1..*\" ];", dummyClassName);
		assertTrue("Missing dependency relations.", actual.contains(expectedDependencies));

		// Check expected fields and methods.
		String[] expectedFields = { "- privateInt : int", "+ publicString : java.lang.String",
				"- privateString : java.lang.String", "+ publicInt : int" };
		String[] expectedMethods = { "- printPrivateString() : void", "getPublicInt() : int",
				"+ getPublicString() : java.lang.String", "# someProtectedMethod() : double" };

		Stream<String> expectedFieldStream = Arrays.stream(expectedFields);
		Stream<String> expectedMethodStream = Arrays.stream(expectedMethods);

		// Test if it has the Fields viewable in the class file.
		expectedFieldStream.forEach(
				(field) -> assertTrue(String.format("Missing expected field: %s", field), actual.contains(field)));

		// Test if it has the Methods viewable in the class file.
		expectedMethodStream.forEach(
				(method) -> assertTrue(String.format("Missing expected method: %s", method), actual.contains(method)));
	}

	@Test
	public void graphVizGeneratorFilter() {
		// Set up the config.
		Configuration config = Configuration.getInstance();
		config.setClasses(Arrays.asList(dummyClassName));
		config.setFilters(data -> data == Modifier.DEFAULT || data == Modifier.PUBLIC);
		config.setNodesep(1.0);
		config.setRecursive(true);
		config.setRankDir("BT");

		// Set up the system model and generator.
		AbstractUMLEngine engine = UMLEngine.getInstance(config);
		IASystemModel systemModel = engine.createSystemModel();
		systemModel = engine.analyze(systemModel);
		String actual = engine.generate(systemModel);

		// Test if it has the basic DOT file styling.
		assertTrue("Missing nodesep.", actual.contains("nodesep=1.0;"));
		assertTrue("Missing node shape.", actual.contains("node [shape=record];"));
		assertTrue("Missing rankdir.", actual.contains("rankdir=BT"));
		assertTrue("Missing primary class name.", actual.contains(String.format("\"%s\"", dummyClassName)));

		// See if it has its expected super class.
		String expectedSuperClass = String.format("\"%s\" -> \"java.lang.Object\" [arrowhead=\"onormal\" style=\"\" ];",
				dummyClassName);
		assertTrue("Missing super class relation.", actual.contains(expectedSuperClass));

		// See if it has its expected dependencies.
		String expectedDependencies = String.format(
				"\"%s\" -> \"java.lang.String\" [arrowhead=\"vee\" style=\"\" taillabel=\"1..*\" ];", dummyClassName);
		assertTrue("Missing dependency relations.", actual.contains(expectedDependencies));

		// Set up expected fields and methods.
		String[] expectedFields = { "+ publicString : java.lang.String", "+ publicInt : int" };
		String[] expectedMethods = { "getPublicInt() : int", "+ getPublicString() : java.lang.String" };
		Stream<String> expectedFieldStream = Arrays.stream(expectedFields);
		Stream<String> expectedMethodStream = Arrays.stream(expectedMethods);

		// Test if it has the Fields viewable in the class file.
		expectedFieldStream.forEach(
				(field) -> assertTrue(String.format("Missing expected field: %s", field), actual.contains(field)));

		// Test if it has the Methods viewable in the class file.
		expectedMethodStream.forEach(
				(method) -> assertTrue(String.format("Missing expected method: %s", method), actual.contains(method)));
	}

	@Test
	public void graphVizWrite() throws IOException {
		// Create a TemporaryFolder that will be deleted after the test runs.
		File directory = this.folder.newFolder("testDirectory");

		// Set up config.
		Configuration config = Configuration.getInstance();
		config.setClasses(Arrays.asList(dummyClassName));
		config.setRecursive(true);
		config.setFileName("testWrite");
		config.setOutputFormat("svg");
		config.setExecutablePath("dot");
		config.setRankDir("BT");
		config.setOutputDirectory(directory.toString());

		// Set up a System Model.
		AbstractUMLEngine engine = UMLEngine.getInstance(config);
		IASystemModel systemModel = engine.createSystemModel();
		systemModel = engine.analyze(systemModel);
		String graphVizString = engine.generate(systemModel);
		engine.executeRunner(graphVizString);
	}

//	@Test
//	public void graphVizManyNoFields() {
//		// Set up config.
//		Configuration config = Configuration.getInstance();
//		config.setFilters(data -> data == Modifier.DEFAULT || data == Modifier.PUBLIC);
//		config.setNodesep(1.0);
//		config.setRecursive(true);
//		config.setRankDir("BT");
//		List<String> classList = new ArrayList<>();
//		classList.add(RelDummyManyClass.class.getName());
//		classList.add(RelOtherDummyClass.class.getName());
//		classList.add(RelDummyClass.class.getName());
//		config.setClasses(classList);
//
//		// Set up SystemModel and Generator.
//		AbstractUMLEngine engine = UMLEngine.getInstance(config);
//		IASystemModel systemModel = engine.createSystemModel();
//		
//		// System Model Verification.
//		Iterable<? extends IRelation> relations = systemModel.getRelations();
//		boolean hasExpectedDependency1 = false;
//		boolean hasExpectedDependency2 = false;
//		for (IRelation relation : relations) {
//			if (relation.getFrom().equals(RelDummyManyClass.class.getName())
//					&& relation.getTo().equals(RelOtherDummyClass.class.getName())) {
//				hasExpectedDependency1 = true;
//			} else if (relation.getFrom().equals(RelDummyManyClass.class.getName())
//					&& relation.getTo().equals("dummy.RelDummyClass")) {
//				hasExpectedDependency2 = true;
//			}
//		}
//		assertTrue("Missing expected array dependency", hasExpectedDependency1);
//		assertTrue("Missing expected generic dependency", hasExpectedDependency2);
//
//		String actual = engine.generate(systemModel);
//		String expectedDependencyCardinality = "\"dummy.RelDummyManyClass\" ->	 \"dummy.RelOtherDummyClass\" [arrowhead=\"vee\" style=\"dashed\"	 headlabel=\"1..*\" ];";
//		assertTrue("Missing GraphViz dependency", actual.contains(expectedDependencyCardinality));
//	}

}