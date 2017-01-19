package config;

import org.junit.Test;

import utility.IFilter;
import utility.Modifier;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CommandLineParserTest {

    @Test
    public void testCommandAllShortFlags() throws Exception {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f public -k -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        assertEquals(ClassParserConfiguration.MODIFIER_FILTER_PUBLIC, conf.getValue(ClassParserConfiguration.MODIFIER_FILTER));
        assertTrue(Boolean.parseBoolean(conf.getValue(ModelConfiguration.IS_RECURSIVE_KEY)));
        for (String cla : conf.getList(ModelConfiguration.CLASSES_KEY))
            assertEquals("me", cla);
        assertEquals("exepath", conf.getValue(RunnerConfiguration.EXECUTABLE_PATH));
        assertEquals("outfile", conf.getValue(RunnerConfiguration.FILE_NAME));
        assertEquals("outdir", conf.getValue(RunnerConfiguration.OUTPUT_DIRECTORY));
        assertEquals(10, Math.round(Double.parseDouble(conf.getValue(GeneratorConfiguration.NODE_SEP))));
        assertEquals("extension", conf.getValue(RunnerConfiguration.OUTPUT_FORMAT));
        assertEquals("TB", conf.getValue(GeneratorConfiguration.RANK_DIR));
    }

    @Test
    public void testCommandAllLongFlags() throws Exception {
        String[] args = "--executable exepath --directory outdir --outputfile outfile --extension extension --filters public --direction --nodesep 10 --recursive me"
                .split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        assertEquals(ClassParserConfiguration.MODIFIER_FILTER_PUBLIC, conf.getValue(ClassParserConfiguration.MODIFIER_FILTER));
        assertTrue(Boolean.parseBoolean(conf.getValue(ModelConfiguration.IS_RECURSIVE_KEY)));
        for (String cla : conf.getList(ModelConfiguration.CLASSES_KEY))
            assertEquals("me", cla);
        assertEquals("exepath", conf.getValue(RunnerConfiguration.EXECUTABLE_PATH));
        assertEquals("outfile", conf.getValue(RunnerConfiguration.FILE_NAME));
        assertEquals("outdir", conf.getValue(RunnerConfiguration.OUTPUT_DIRECTORY));
        assertEquals(10, Math.round(Double.parseDouble(conf.getValue(GeneratorConfiguration.NODE_SEP))));
        assertEquals("extension", conf.getValue(RunnerConfiguration.OUTPUT_FORMAT));
        assertEquals("TB", conf.getValue(GeneratorConfiguration.RANK_DIR));
    }

    @Test
    public void testNodeSep() throws Exception {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f public -n 10.1 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();
        assertEquals(ClassParserConfiguration.MODIFIER_FILTER_PUBLIC, conf.getValue(ClassParserConfiguration.MODIFIER_FILTER));
        assertEquals(101, Math.round(10 * Double.parseDouble(conf.getValue(GeneratorConfiguration.NODE_SEP))));
    }

    @Test
    public void testMultipleClasses() throws Exception {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f private -n 10 -r me you us".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        ArrayList<String> classes = new ArrayList<String>();
        for (String cla : conf.getList(ModelConfiguration.CLASSES_KEY))
            classes.add(cla);

        assertEquals(3, classes.size());
        assertTrue(classes.contains("me"));
        assertTrue(classes.contains("you"));
        assertTrue(classes.contains("us"));

    }

    @Test
    public void testEmptyClasses() throws Exception {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f private -n 10 -r".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        ArrayList<String> classes = new ArrayList<>();
        for (String cla : conf.getList(ModelConfiguration.CLASSES_KEY))
            classes.add(cla);

        assertEquals(1, classes.size());
        assertTrue(classes.contains("java.lang.String"));
    }

    @Test
    public void testRecus() throws Exception {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f private -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        assertTrue(Boolean.parseBoolean(conf.getValue(ModelConfiguration.IS_RECURSIVE_KEY)));

        args = "-e exepath -d outdir -o outfile -x extension -f private -n 10 me".split(" ");

        com = new CommandLineParser(args);

        conf = com.create();

        assertTrue(!Boolean.parseBoolean(conf.getValue(ModelConfiguration.IS_RECURSIVE_KEY)));
    }

    @Test
    public void testPrivate() throws Exception {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f private -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        assertEquals(ClassParserConfiguration.MODIFIER_FILTER_PRIVATE, conf.getValue(ClassParserConfiguration.MODIFIER_FILTER));
    }

    @Test
    public void testProtected() throws Exception {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f protected -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();


        assertEquals(ClassParserConfiguration.MODIFIER_FILTER_PROTECTED, conf.getValue(ClassParserConfiguration.MODIFIER_FILTER));
    }

    @Test
    public void testDefault() throws Exception {
        String[] args = "-e exepath -d outdir -o outfile -x extension -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        assertEquals(ClassParserConfiguration.MODIFIER_FILTER_PRIVATE, conf.getValue(ClassParserConfiguration.MODIFIER_FILTER));
    }

    @Test
    public void testRankDirDefault() throws Exception {
        String[] args = "-e exepath -d outdir -o outfile -x extension -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        assertEquals("BT", conf.getValue(GeneratorConfiguration.RANK_DIR));
    }

    @Test
    public void TestJComponentArgument() throws Exception {
        String[] args = new String[]{"-e", "dot", "-r", "-d", "output", "-o", "Jcomponent", "--filters", "public",
                "-x", "png", "javax.swing.JComponent"};

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();
        assertEquals(ClassParserConfiguration.MODIFIER_FILTER_PUBLIC, conf.getValue(ClassParserConfiguration.MODIFIER_FILTER));
    }
}
