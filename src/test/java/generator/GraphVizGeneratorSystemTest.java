package generator;

import config.Configuration;
import dummy.GenDummyClass;
import dummy.RelDummyClass;
import dummy.RelDummyManyClass;
import dummy.RelOtherDummyClass;
import generator.relationshipParser.Relation;
import model.SystemModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import runner.GraphVizRunner;
import runner.IRunner;
import utility.Modifier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The GraphVizGenerator and GraphVizRunner Test.
 * <p>
 * Created by lamd on 12/11/2016.
 */
public class GraphVizGeneratorSystemTest {
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void graphVizGenerate() throws IOException {
		String dummyClassName = GenDummyClass.class.getName();
		// Set up the system model and config.
		Configuration config = Configuration.getInstance();
		config.setClasses(Arrays.asList(dummyClassName));
		config.setRecursive(true);
		config.setNodesep(1.0);
		config.setRankDir("BT");

		ISystemModel systemModel = SystemModel.getInstance(config);

		// Create GraphVizGenerator.
		IGenerator generator = new GraphVizGenerator(config);

		String actual = generator.generate(systemModel);

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
//		String expectedDependencies = String.format(
//				"\"%s\" -> \"java.lang.String\" [arrowhead=\"vee\" style=\"\" taillabel=\"1..*\" ];", dummyClassName);
//		assertTrue("Missing dependency relations.", actual.contains(expectedDependencies));

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
		String dummyClassName = GenDummyClass.class.getName();
		// Set up the system model and config.
		Configuration config = Configuration.getInstance();
		List<String> classList = new ArrayList<>();
		classList.add(dummyClassName);
		config.setClasses(classList);
		config.setRecursive(true);
		config.setFilters(data -> data == Modifier.DEFAULT || data == Modifier.PUBLIC);
		config.setNodesep(1.0);
		config.setRecursive(true);
		config.setRankDir("BT");

		ISystemModel systemModel = SystemModel.getInstance(config);

		// Set up config and generator.
		IGenerator generator = new GraphVizGenerator(config);

		String actual = generator.generate(systemModel);

		// Test if it has the basic DOT file styling.
		assertTrue("Missing nodesep.", actual.contains("nodesep=1.0;"));
		assertTrue("Missing node shape.", actual.contains("node [shape=record];"));
		assertTrue("Missing rankdir.", actual.contains("rankdir=BT"));
		assertTrue("Missing primary class name.", actual.contains(String.format("\"%s\"", dummyClassName)));

		// See if it has its expected super class.
		String expectedSuperClass = String.format("\"%s\" -> \"java.lang.Object\" [arrowhead=\"onormal\" style=\"\" ];",
				dummyClassName);
		assertTrue("Missing super class relation.", actual.contains(expectedSuperClass));

//		String expectedDependencies = String.format("\"%s\" -> \"java.lang.String\" [arrowhead=\"vee\" style=\"\"];",
//				dummyClassName);
//		assertTrue("Missing dependency relations.", actual.contains(expectedDependencies));

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
		String dummyClassName = GenDummyClass.class.getName();
		// Create a TemporaryFolder that will be deleted after the test runs.
		File directory = this.folder.newFolder("testDirectory");

		// Set up a System Model.
		Configuration config = Configuration.getInstance();
		List<String> classList = new ArrayList<>();
		classList.add(dummyClassName);
		config.setClasses(classList);
		config.setRecursive(true);

		config.setFileName("testWrite");
		config.setOutputFormat("svg");
		config.setExecutablePath("dot");
		config.setRankDir("BT");
		config.setOutputDirectory(directory.toString());

		ISystemModel systemModel = SystemModel.getInstance(config);

		// generate the string
		IGenerator generator = new GraphVizGenerator(config);
		String graphVizString = generator.generate(systemModel);

		internalRunner(config, graphVizString);
	}

	// @Test
	// public void graphVizManyNoFields() {
	// String relDummyManyClass = RelDummyManyClass.class.getName();
	// String RelOtherDummyClass = RelOtherDummyClass.class.getName();
	// String RelDummyClass = RelDummyClass.class.getName();
	// // Set up the system model and config.
	// Configuration config = Configuration.getInstance();
	// config.setClasses(Arrays.asList(relDummyManyClass, RelOtherDummyClass));
	// config.setRecursive(true);
	// config.setFilters(data -> data == Modifier.DEFAULT || data ==
	// Modifier.PUBLIC);
	// config.setNodesep(1.0);
	// config.setRecursive(true);
	// config.setRankDir("BT");
	//
	// ISystemModel systemModel = SystemModel.getInstance(config);
	//
	// IGenerator generator = new GraphVizGenerator(config);
	//
	// // System Model Verification.
	// Iterable<Relation> relations = systemModel.getRelations();
	// boolean hasExpectedDependency1 = false;
	// boolean hasExpectedDependency2 = false;
	// for (Relation relation : relations) {
	// if (relation.getFrom().equals(relDummyManyClass) &&
	// relation.getTo().equals(RelOtherDummyClass)) {
	// hasExpectedDependency1 = true;
	// } else if (relation.getFrom().equals(RelOtherDummyClass) &&
	// relation.getTo().equals(RelDummyClass)) {
	// hasExpectedDependency2 = true;
	// }
	// }
	// assertTrue("Missing expected array dependency", hasExpectedDependency1);
	// assertTrue("Missing expected generic dependency",
	// hasExpectedDependency2);
	//
	// String actual = generator.generate(systemModel);
	// // String expectedDependencyCardinality = "\"dummy.RelDummyManyClass\"
	// // -> \"dummy.RelOtherDummyClass\" [arrowhead=\"vee\" style=\"dashed\"
	// // headlabel=\"1..*\" ];";
	// // assertTrue("Missing GraphViz dependency",
	// // actual.contains(expectedDependencyCardinality));
	// }

	/**
	 * Interal Testing Runner method to call for actual output.
	 *
	 * @param config
	 *            Configuration for the runner to use.
	 * @param graphVizString
	 *            GraphViz DOT string
	 */
	private void internalRunner(Configuration config, String graphVizString) {
		// Create the runner
		IRunner runner = new GraphVizRunner(config);

		try {
			runner.execute(graphVizString);
			File file = new File(config.getOutputDirectory(), config.getFileName() + "." + config.getOutputFormat());
			assertTrue("Unable to detect output file", file.exists());
		} catch (Exception e) {
			System.err.println("[ INFO ]: Ensure that GraphViz bin folder is set in the environment variable.");
			fail("[ ERROR ]: An Exception has occurred!\n" + e.getMessage());
			e.printStackTrace();
		}
	}
}