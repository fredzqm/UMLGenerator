package generator;

import config.Configuration;
import dummy.GenDummyClass;
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

import static org.junit.Assert.*;

/**
 * The GraphVizGenerator and GraphVizRunner Test.
 * <p>
 * Created by lamd on 12/11/2016.
 */
public class GraphVizGeneratorSystemTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    public String dummyClassName = GenDummyClass.class.getPackage().getName() + "." + GenDummyClass.class.getSimpleName();
    
    private ISystemModel setupSystemModel() {
        Configuration config = Configuration.getInstance();
        List<String> classList = new ArrayList<>();
        classList.add(dummyClassName);
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
        config.setParseKey("default");

        // Create GraphVizGenerator.
        IGenerator generator = new GraphVizGenerator(config);

        String actual = generator.generate(systemModel);

        // Test if it has the basic DOT file styling.
        assertTrue(actual.contains("nodesep=1.0;"));
        assertTrue(actual.contains("node [shape=record];"));
        assertTrue(actual.contains("rankdir=BT"));
        assertTrue(actual.contains("\""+dummyClassName+"\""));
        assertTrue(actual.contains("\""+dummyClassName+"\" -> \"java.lang.Object\";"));
        assertTrue(actual.contains("edge [arrowhead=vee style=dashed ]"));
        assertTrue(actual.contains("edge [arrowhead=onormal style=\"\" ]"));
        assertTrue(actual.contains("\""+dummyClassName+"\" -> \"java.lang.Object\""));

        // Count how many relations there are.
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
        config.setFilters(data -> data == Modifier.DEFAULT || data == Modifier.PUBLIC);
        config.setNodesep(1.0);
        config.setRecursive(true);
        config.setRankDir("BT");
        config.setParseKey("default");

        IGenerator generator = new GraphVizGenerator(config);

        String actual = generator.generate(systemModel);
        
        // Test if it has the basic DOT file styling.
        assertTrue(actual.contains("nodesep=1.0;"));
        assertTrue(actual.contains("node [shape=record];"));
        assertTrue(actual.contains("rankdir=BT"));
        assertTrue(actual.contains("\""+dummyClassName+"\""));
        assertTrue(actual.contains("\""+dummyClassName+"\" -> \"java.lang.Object\";"));
        assertTrue(actual.contains("edge [arrowhead=vee style=dashed ]"));
        assertTrue(actual.contains("edge [arrowhead=onormal style=\"\" ]"));
        assertTrue(actual.contains("\""+dummyClassName+"\" -> \"java.lang.Object\""));

        // Count how many relations there are.
        String expectedSuperClass = "\""+dummyClassName+"\" -> \"java.lang.Object\";";
        assertTrue(actual.contains(expectedSuperClass));

        String expectedDependencies = "\""+dummyClassName+"\" -> \"java.lang.String\";";
        assertTrue(actual.contains(expectedDependencies));

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
        config.setFileName("testWrite");
        config.setOutputFormat("svg");
        config.setExecutablePath("dot");
        config.setRankDir("BT");
        config.setParseKey("default");
        config.setOutputDirectory(directory.toString());
        
        // generate the string
        IGenerator generator = new GraphVizGenerator(config);
        String graphVizString = generator.generate(systemModel);

        internalRunner(config, graphVizString);
    }

    /**
     * Interal Testing Runner method to call for actual output.
     *
     * @param config         Configuration for the runner to use.
     * @param graphVizString GraphViz DOT string
     */
    private void internalRunner(Configuration config, String graphVizString) {
        // Create the runner
        IRunner runner = new GraphVizRunner(config);
        config.setOutputDirectory("./output");

        try {
            runner.execute(graphVizString);
            File file = new File(config.getOutputDirectory(), config.getFileName() + "." + config.getOutputFormat());
            assertTrue(file.exists());
        } catch (Exception e) {
            System.err.println("[ INFO ]: Ensure that GraphViz bin folder is set in the environment variable.");
            fail("[ ERROR ]: An Exception has occurred!\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}