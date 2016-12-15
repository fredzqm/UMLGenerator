package generator;

import config.Configuration;
import model.Modifier;
import model.SystemModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import runner.GraphVizRunner;
import runner.IRunner;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * The GraphVizGenerator and GraphVizRunner Test.
 * <p>
 * Created by lamd on 12/11/2016.
 */
public class GraphVizGeneratorTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private ISystemModel setupSystemModel() {
        Configuration config = Configuration.getInstance();
        List<String> classList = new ArrayList<>();
        classList.add(DummyClass.class.getPackage().getName() + "." + DummyClass.class.getSimpleName());
        classList.add("java.lang.String");
        config.setClasses(classList);
        config.setRecursive(true);
        return SystemModel.getInstance(config);
    }

    @Test
    public void graphVizGenerate() throws IOException {
        // Set up the system model and config.
        ISystemModel systemModel = setupSystemModel();

        Configuration config = Configuration.getInstance();
        config.setNodesep(1.0);
        config.setRankDir("BT");

        // Create GraphVizGenerator.
        IGenerator generator = new GraphVizGenerator(config);

        String actual = generator.generate(systemModel, null);

        // Test if it has the basic DOT file styling.
        assertTrue(actual.contains("nodesep=1.0;"));
        assertTrue(actual.contains("node [shape=record];"));
        assertTrue(actual.contains("rankdir=BT"));
        assertTrue(actual.contains("\"generator.DummyClass\""));
        assertTrue(actual.contains("\"generator.DummyClass\" -> {\"java.lang.Object\"};"));
        assertTrue(actual.contains("\"generator.DummyClass\" -> {}"));
        assertTrue(actual.contains("edge [arrowhead=vee style=dashed]"));
        assertTrue(actual.contains("edge [arrowhead=onormal]"));
        assertTrue(actual.contains("\"generator.DummyClass\" -> {\"java.lang.Object\"}"));

        // Count how many relations there are.
        // TODO: When Fred implements Has-A and Depends-On update this test.
        int relationsCount = 0;
        int index = actual.indexOf("\"generator.DummyClass\" -> {}");
        while (index != -1) {
            relationsCount++;
            index = actual.indexOf("\"generator.DummyClass\" -> {}", index + 1);
        }
        assertEquals("Number of Relations not equal", 3, relationsCount);

        String[] expectedFields = {"- privateInt : int", "+ publicString : java.lang.String",
                "- privateString : java.lang.String", "+ publicInt : int"};
        String[] expectedMethods = {"- printPrivateString() : void", "getPublicInt() : int",
                "+ getPublicString() : java.lang.String", "# someProtectedMethod() : double"};

        Stream<String> expectedFieldStream = Arrays.stream(expectedFields);
        Stream<String> expectedMethodStream = Arrays.stream(expectedMethods);

        // Test if it has the Fields viewable in the class file.
        expectedFieldStream.forEach((field) -> assertTrue(actual.contains(field)));

        // Test if it has the Methods viewable in the class file.
        expectedMethodStream.forEach((method) -> assertTrue(actual.contains(method)));
    }

    @Test
    public void graphVizGeneratorFilter() {
        // Set up the system model and config.
        ISystemModel systemModel = setupSystemModel();

        // Set up config.
        Configuration config = Configuration.getInstance();
        Set<IModifier> filters = new HashSet<>();
        filters.add(Modifier.PROTECTED);
        filters.add(Modifier.PRIVATE);
        config.setFilters(filters);
        config.setNodesep(1.0);
        config.setRecursive(true);
        config.setRankDir("BT");

        // FIXME: Remove test code.
//        config.setOutputDirectory("./output");
//        config.setFileName("testFilter");
//        config.setExecutablePath("dot");

        // Create GraphVizGenerator.
        IGenerator generator = new GraphVizGenerator(config);

        String actual = generator.generate(systemModel, null);

//        internalRunner(config, actual); // FIXME: Remove after testing is done.

        // Test if it has the basic DOT file styling.
        assertTrue(actual.contains("nodesep=1.0;"));
        assertTrue(actual.contains("node [shape=record];"));
        assertTrue(actual.contains("rankdir=BT"));
        assertTrue(actual.contains("\"generator.DummyClass\""));
        assertTrue(actual.contains("\"generator.DummyClass\" -> {\"java.lang.Object\"};"));
        assertTrue(actual.contains("\"generator.DummyClass\" -> {}"));
        assertTrue(actual.contains("edge [arrowhead=vee style=dashed]"));
        assertTrue(actual.contains("edge [arrowhead=onormal]"));
        assertTrue(actual.contains("\"generator.DummyClass\" -> {\"java.lang.Object\"}"));

        // Count how many relations there are.
        // TODO: When Fred implements Has-A and Depends-On update this test.
        int relationsCount = 0;
        int index = actual.indexOf("\"generator.DummyClass\" -> {}");
        while (index != -1) {
            relationsCount++;
            index = actual.indexOf("\"generator.DummyClass\" -> {}", index + 1);
        }
        assertEquals("Number of Relations not equal", 3, relationsCount);

        String[] expectedFields = {"+ publicString : java.lang.String", "+ publicInt : int"};
        String[] expectedMethods = {"getPublicInt() : int", "+ getPublicString() : java.lang.String"};

        Stream<String> expectedFieldStream = Arrays.stream(expectedFields);
        Stream<String> expectedMethodStream = Arrays.stream(expectedMethods);

        // Test if it has the Fields viewable in the class file.
        expectedFieldStream.forEach((field) -> assertTrue(actual.contains(field)));

        // Test if it has the Methods viewable in the class file.
        expectedMethodStream.forEach((method) -> assertTrue(actual.contains(method)));
    }

    @Test
    public void graphVizWrite() throws IOException {
        // Create a TemporaryFolder that will be deleted after the test runs.
        File directory = this.folder.newFolder("testDirectory");

        // Set up a System Model.
        ISystemModel systemModel = setupSystemModel();
        Configuration config = Configuration.getInstance();
        config.setFileName("test");
        config.setOutputFormat("png");
        config.setExecutablePath("dot");

        // Set the output directory to the root of the Temporary Folder.
        config.setOutputDirectory(directory.toString());

        // generate the string
        IGenerator generator = new GraphVizGenerator(config);
        String graphVizString = generator.generate(systemModel, null);

        internalRunner(config, graphVizString);
    }

    /**
     * Interal Testing Runner method to call for actual output.
     *
     * @param config         Configuration for the runner to use.
     * @param graphVizString GraphViz DOT string t
     */
    private void internalRunner(Configuration config, String graphVizString) {
        // Create the runner
        IRunner runner = new GraphVizRunner();
        try {
            runner.execute(config, graphVizString);
            File file = new File(config.getOutputDirectory(), config.getFileName() + "." + config.getOutputFormat());
            assertTrue(file.exists());
        } catch (Exception e) {
            System.err.println("[ INFO ]: Ensure that GraphViz bin folder is set in the environment variable.");
            fail("[ ERROR ]: An Exception has occured!\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}