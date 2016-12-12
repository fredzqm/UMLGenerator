package generator;

import configs.IConfiguration;
import configs.IFormat;
import models.ASMParser;
import models.SystemModel;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import runner.GraphVizRunner;
import runner.IRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The GraphVizGenerator and GraphVizRunner Test.
 * <p>
 * Created by lamd on 12/11/2016.
 */
public class GraphVizGeneratorTest {
    private ISystemModel setupSystemModel() {
        List<String> classList = new ArrayList<>();
        classList.add("java.lang.String");
        return new SystemModel(classList, new ASMParser());
    }

    @Test
    public void generate() throws Exception {
        // Set up the system model and config.
        ISystemModel systemModel = setupSystemModel();
        IConfiguration config = new DummyConfig();

        // Create GraphVizGenerator.
        IGenerator generator = new GraphVizGenerator();
        generator.generate(systemModel, config, null);
        String expected = "digraph GraphVizGeneratedDOT {\n" +
                "\tnodesep=1.0;\n" +
                "\tnode [shape=record];\n" +
                "\t\"java.lang.String\" [\n" +
                "\t\tlabel = \"{java.lang.String | - serialPersistentFields : java.io.ObjectStreamField[]\\l+ CASE_INSENSITIVE_ORDER : java.util.Comparator\\l- serialVersionUID : long\\l- value : char[]\\l- hash : int\\l | + codePointAt(int) : int\\l + getBytes(java.nio.charset.Charset) : byte[]\\l + indexOf(java.lang.String, int) : int\\l + equalsIgnoreCase(java.lang.String) : boolean\\l + lastIndexOf(int) : int\\l + trim() : java.lang.String\\l + endsWith(java.lang.String) : boolean\\l + replace(java.lang.CharSequence, java.lang.CharSequence) : java.lang.String\\l + contentEquals(java.lang.StringBuffer) : boolean\\l + indexOf(int, int) : int\\l + compareTo(java.lang.Object) : int\\l + toUpperCase() : java.lang.String\\l + replace(char, char) : java.lang.String\\l # finalize() : void\\l + wait() : void\\l + intern() : java.lang.String\\l + matches(java.lang.String) : boolean\\l + subSequence(int, int) : java.lang.CharSequence\\l + hashCode() : int\\l + toLowerCase() : java.lang.String\\l + lastIndexOf(java.lang.String) : int\\l + isEmpty() : boolean\\l + toString() : java.lang.String\\l + toLowerCase(java.util.Locale) : java.lang.String\\l + split(java.lang.String, int) : java.lang.String[]\\l + notify() : void\\l + contains(java.lang.CharSequence) : boolean\\l + substring(int, int) : java.lang.String\\l + split(java.lang.String) : java.lang.String[]\\l + lastIndexOf(java.lang.String, int) : int\\l + equals(java.lang.Object) : boolean\\l + startsWith(java.lang.String, int) : boolean\\l + lastIndexOf(int, int) : int\\l + wait(long) : void\\l + indexOf(int) : int\\l + compareToIgnoreCase(java.lang.String) : int\\l + codePointCount(int, int) : int\\l # clone() : java.lang.Object\\l + contentEquals(java.lang.CharSequence) : boolean\\l - lastIndexOfSupplementary(int, int) : int\\l + getClass() : java.lang.Class\\l + getBytes(java.lang.String) : byte[]\\l + getChars(int, int, char[], int) : void\\l + codePointBefore(int) : int\\l - nonSyncContentEquals(java.lang.AbstractStringBuilder) : boolean\\l + concat(java.lang.String) : java.lang.String\\l + getBytes() : byte[]\\l + substring(int) : java.lang.String\\l + getBytes(int, int, byte[], int) : void\\l + length() : int\\l - indexOfSupplementary(int, int) : int\\l + indexOf(java.lang.String) : int\\l + offsetByCodePoints(int, int) : int\\l + toUpperCase(java.util.Locale) : java.lang.String\\l + compareTo(java.lang.String) : int\\l + regionMatches(boolean, int, java.lang.String, int, int) : boolean\\l + regionMatches(int, java.lang.String, int, int) : boolean\\l + replaceFirst(java.lang.String, java.lang.String) : java.lang.String\\l  getChars(char[], int) : void\\l + startsWith(java.lang.String) : boolean\\l + notifyAll() : void\\l + toCharArray() : char[]\\l + charAt(int) : char\\l + replaceAll(java.lang.String, java.lang.String) : java.lang.String\\l + wait(long, int) : void\\l }\"\n" +
                "\t];\n" +
                "\tedge [arrowhead=onormal];\n" +
                "\t\"java.lang.String\" -> {\"java.lang.Object\"};\n" +
                "\n" +
                "\tedge [arrowhead=onormal, style=dashed];\n" +
                "\t\"java.lang.String\" -> {\"java.io.Serializable\", \"java.lang.Comparable\", \"java.lang.CharSequence\"};\n" +
                "\n" +
                "\tedge [arrowhead=vee];\n" +
                "\t\"java.lang.String\" -> {}\n" +
                "\tedge [arrowhead=vee style=dashed];\n" +
                "\t\"java.lang.String\" -> {}\n" +
                "}";
        assertEquals("Generator Output String is not equal", expected, generator.getOutputString());
    }

    @Test
    public void write() throws IOException {
        // Create a TemporaryFolder that will be deleted after the test runs.
        TemporaryFolder folder = new TemporaryFolder();
        folder.create();

        // Set up a System Model.
        List<String> classList = new ArrayList<>();
        classList.add("java.lang.String");
        ISystemModel systemModel = new SystemModel(classList, new ASMParser());
        IConfiguration config = new DummyConfig();

        // Set the output directory to the root of the Temporary Folder.
        config.setOutputDirectory(folder.getRoot().toString());

        // Create the runner
        IRunner runner = new GraphVizRunner(systemModel, config, null);
        try {
            // FIXME: Replace this with the dot.exe path.
            runner.execute("C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe", config.getOutputDirectory(), config.getOutputFormat(), config.getFileName());
            File file = new File(config.getOutputDirectory() + "/" + config.getFileName() + "." + config.getOutputFormat());
            assertTrue(file.exists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A Dummy Configuration Object used for testing.
     */
    private class DummyConfig implements IConfiguration {
        private String fileName, outputDirectory, outputFormat;

        /**
         * Constructs a basic DummyConfig object.
         */
        DummyConfig() {
            this.outputDirectory = "./output";
            this.fileName = "test";
            this.outputFormat = "png";
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
    }
}