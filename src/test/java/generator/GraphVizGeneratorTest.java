package generator;

import config.Configuration;
import model.ASMParser;
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
        List<String> classList = new ArrayList<>();
        classList.add(DummyClass.class.getPackage().getName() + "." + DummyClass.class.getSimpleName());
        return new SystemModel(classList, new ASMParser());
    }

    @Test
    public void graphVizGenerate() throws IOException {
        // Set up the system model and config.
        ISystemModel systemModel = setupSystemModel();

        Configuration config = Configuration.getInstance();
        config.setNodesep(1.0);

        // Create GraphVizGenerator.
        IGenerator generator = new GraphVizGenerator(config);

        String actual = generator.generate(systemModel, null);

        // Test if it has the basic DOT file styling.
        assertTrue(actual.contains("\tnodesep=1.0;\n"));
        assertTrue(actual.contains("\tnode [shape=record];\n"));
        assertTrue(actual.contains("\t\"generator.DummyClass\" [\n"));
        assertTrue(actual.contains("\t\"generator.DummyClass\" -> {\"java.lang.Object\"};\n"));
        assertTrue(actual.contains("\t\"generator.DummyClass\" -> {}\n"));
        assertTrue(actual.contains("edge [arrowhead=vee style=dashed]"));
        assertTrue(actual.contains("edge [arrowhead=onormal]"));
        assertTrue(actual.contains("\"generator.DummyClass\" -> {\"java.lang.Object\"}"));

        // Count how many relations there are.
        // TODO: When Fred implements Has-A and Depends-On update this test.
        int relationsCount = 0;
        int index = actual.indexOf("\t\"generator.DummyClass\" -> {}\n");
        while (index != -1) {
            relationsCount++;
            index = actual.indexOf("\t\"generator.DummyClass\" -> {}\n", index + 1);
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

        // Create GraphVizGenerator.
        IGenerator generator = new GraphVizGenerator(config);

        String actual = generator.generate(systemModel, null);

        // Test if it has the basic DOT file styling.
        assertTrue(actual.contains("\tnodesep=1.0;\n"));
        assertTrue(actual.contains("\tnode [shape=record];\n"));
        assertTrue(actual.contains("\t\"generator.DummyClass\" [\n"));
        assertTrue(actual.contains("\t\"generator.DummyClass\" -> {\"java.lang.Object\"};\n"));
        assertTrue(actual.contains("\t\"generator.DummyClass\" -> {}\n"));
        assertTrue(actual.contains("edge [arrowhead=vee style=dashed]"));
        assertTrue(actual.contains("edge [arrowhead=onormal]"));
        assertTrue(actual.contains("\"generator.DummyClass\" -> {\"java.lang.Object\"}"));

        // Count how many relations there are.
        // TODO: When Fred implements Has-A and Depends-On update this test.
        int relationsCount = 0;
        int index = actual.indexOf("\t\"generator.DummyClass\" -> {}\n");
        while (index != -1) {
            relationsCount++;
            index = actual.indexOf("\t\"generator.DummyClass\" -> {}\n", index + 1);
        }
        assertEquals("Number of Relations not equal", 3, relationsCount);

        String[] expectedFields = {"+ publicString : java.lang.String", "+ publicInt : int"};
        String[] expectedMethods = {"getPublicInt() : int",
                "+ getPublicString() : java.lang.String"};

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
        config.setOutputDirectory("./output");
        config.setOutputFormat("png");
        config.setExecutablePath("dot");

        // Set the output directory to the root of the Temporary Folder.
        config.setOutputDirectory(directory.toString());

        // Create the runner
        IRunner runner = new GraphVizRunner();
        IGenerator generator = new GraphVizGenerator(config);
        String graphVizString = generator.generate(systemModel, null);

        try {
            System.out.println("[ INFO ]: Ensure that GraphViz bin folder is set in the environment variable.");
            runner.execute(config, graphVizString);
            File file = new File(directory, config.getFileName() + "." + config.getOutputFormat());
            assertTrue(file.exists());
        } catch (Exception e) {
            fail("[ ERROR ]: An Exception has occured!\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}