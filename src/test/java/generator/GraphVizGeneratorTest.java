package generator;

import configs.IConfiguration;
import configs.IFormat;
import models.ASMParser;
import models.SystemModel;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import runner.GraphVizRunner;
import runner.IRunner;
import runner.IRunnerConfiguration;

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
public class GraphVizGeneratorTest {
    private ISystemModel setupSystemModel() {
        List<String> classList = new ArrayList<>();
        classList.add(DummyClass.class.getPackage().getName() + "." + DummyClass.class.getSimpleName());
        return new SystemModel(classList, new ASMParser());
    }

    @Test
    public void generate() throws IOException {
        // Set up the system model and config.
        ISystemModel systemModel = setupSystemModel();
        IGeneratorConfiguration config = new DummyConfig();

        // Create GraphVizGenerator.
        IGenerator generator = new GraphVizGenerator();
        generator.generate(systemModel, config, null);

        String actual = generator.getOutputString();

        // Test if it has the basic DOT file styling.
        assertTrue(actual.contains("\tnodesep=1.0;\n"));
        assertTrue(actual.contains("\tnode [shape=record];\n"));
        assertTrue(actual.contains("\t\"generator.DummyClass\" [\n"));
        assertTrue(actual.contains("\t\"generator.DummyClass\" -> {\"java.lang.Object\"};\n"));
        assertTrue(actual.contains("\t\"generator.DummyClass\" -> {}\n"));
        assertTrue(actual.contains("edge [arrowhead=vee style=dashed]"));
        assertTrue(actual.contains("edge [arrowhead=onormal]"));
        assertTrue(actual.contains("\"generator.DummyClass\" -> {\"java.lang.Object\"}"));

        // Count how many relations there are. TODO: When Fred implements Has-A and Depends-On update this test.
        int relationsCount = 0;
        int index = actual.indexOf("\t\"generator.DummyClass\" -> {}\n");
        while (index != -1) {
            relationsCount++;
            index = actual.indexOf("\t\"generator.DummyClass\" -> {}\n", index + 1);
        }
        assertEquals("Number of Relations not equal", 3, relationsCount);

        String[] expectedFields = {"- privateInt : int", "+ publicString : java.lang.String", "- privateString : java.lang.String", "+ publicInt : int"};
        String[] expectedMethods = {"- printPrivateString() : void", "getPublicInt() : int", "+ getPublicString() : java.lang.String", "# someProtectedMethod() : double"};

        Stream<String> expectedFieldStream = Arrays.stream(expectedFields);
        Stream<String> expectedMethodStream = Arrays.stream(expectedMethods);

        // Test if it has the Fields viewable in the class file.
        expectedFieldStream.forEach((field) -> assertTrue(actual.contains(field)));

        // Test if it has the Methods viewable in the class file.
        expectedMethodStream.forEach((method) -> assertTrue(actual.contains(method)));
    }

    @Test
    public void write() throws IOException {
        // Create a TemporaryFolder that will be deleted after the test runs.
        TemporaryFolder folder = new TemporaryFolder();
        folder.create();

        // Set up a System Model.
        ISystemModel systemModel = setupSystemModel();
        IRunnerConfiguration config = new DummyConfig();

        // Set the output directory to the root of the Temporary Folder.
        config.setOutputDirectory(folder.getRoot().toString());

        // Create the runner
        IRunner runner = new GraphVizRunner(systemModel, config, null);
        try {
            // FIXME: Replace this with the dot.exe path.
            runner.execute();
            File file = new File(config.getOutputDirectory() + "/" + config.getFileName() + "." + config.getOutputFormat());
            assertTrue(file.exists());
        } catch (Exception e) {
            fail("[ ERROR ]: An Exception has occured!\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * A Dummy Configuration Object used for testing.
     */
    private class DummyConfig implements IConfiguration, IRunnerConfiguration, IGeneratorConfiguration {
        private String executablePath;
        private String fileName, outputDirectory, outputFormat;
        private double nodeSep;

        /**
         * Constructs a basic DummyConfig object.
         */
        DummyConfig() {
            this.outputDirectory = "./output";
            this.fileName = "test";
            this.outputFormat = "png";
            this.nodeSep = 1.0;
            this.executablePath = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe";
        }

        @Override
        public Iterable<String> getClasses() {
            return null;
        }

        @Override
        public IFormat getFormat() {
            return null;
        }

        @Override
        public String getFileName() {
            return this.fileName;
        }

        @Override
        public void setFileName(String name) {
            this.fileName = name;
        }

        @Override
        public String getOutputDirectory() {
            return this.outputDirectory;
        }

        @Override
        public void setOutputDirectory(String outputDirectory) {
            this.outputDirectory = outputDirectory;
        }

        @Override
        public String getOutputFormat() {
            return this.outputFormat;
        }

        @Override
        public void setOutputFormat(String outputFormat) {
            this.outputFormat = outputFormat;
        }

        @Override
        public String getExecutablePath() {
            return this.executablePath;
        }

        @Override
        public void setExecutablePath(String executablePath) {
            this.executablePath = executablePath;
        }

        @Override
        public double getNodeSep() {
            return this.nodeSep;
        }

        @Override
        public void setNodeSep(double nodeSep) {
            this.nodeSep = nodeSep;
        }
    }
}