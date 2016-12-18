package config;

import org.junit.Test;
import utility.IFilter;
import utility.Modifier;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CommandLineParserTest {

    @Test
    public void testCommandAllShortFlags() {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f public -k -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        IFilter<Modifier> f = conf.getMethodFilters();
        assertFalse(f.filter(Modifier.PRIVATE));
        assertFalse(f.filter(Modifier.PROTECTED));
        assertTrue(f.filter(Modifier.PUBLIC));
        assertTrue(conf.isRecursive());
        for (String cla : conf.getClasses())
            assertEquals("me", cla);
        assertEquals("exepath", conf.getExecutablePath());
        assertEquals("outfile", conf.getFileName());
        assertEquals("outdir", conf.getOutputDirectory());
        assertEquals(10, Math.round(conf.getNodeSep()));
        assertEquals("extension", conf.getOutputFormat());
        assertEquals("TB", conf.getRankDir());
    }

    @Test
    public void testCommandAllLongFlags() {
        String[] args = "--executable exepath --directory outdir --outputfile outfile --extension extension --filters public --direction --nodesep 10 --recursive me"
                .split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        IFilter<Modifier> f = conf.getMethodFilters();

        assertFalse(f.filter(Modifier.PRIVATE));
        assertFalse(f.filter(Modifier.PROTECTED));
        assertTrue(f.filter(Modifier.PUBLIC));
        assertTrue(conf.isRecursive());
        for (String cla : conf.getClasses())
            assertEquals("me", cla);
        assertEquals("exepath", conf.getExecutablePath());
        assertEquals("outfile", conf.getFileName());
        assertEquals("outdir", conf.getOutputDirectory());
        assertEquals(10, Math.round(conf.getNodeSep()));
        assertEquals("extension", conf.getOutputFormat());
        assertEquals("TB", conf.getRankDir());
    }

    @Test
    public void testNodeSep() {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f public -n 10.1 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();
        assertFalse(conf.getMethodFilters().filter(Modifier.PRIVATE));
        assertEquals(101, Math.round(10 * conf.getNodeSep()));
    }

    @Test
    public void testMultipleClasses() {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f private -n 10 -r me you us".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        ArrayList<String> classes = new ArrayList<String>();
        for (String cla : conf.getClasses())
            classes.add(cla);

        assertEquals(3, classes.size());
        assertTrue(classes.contains("me"));
        assertTrue(classes.contains("you"));
        assertTrue(classes.contains("us"));

    }

    @Test
    public void testEmptyClasses() {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f private -n 10 -r".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        ArrayList<String> classes = new ArrayList<String>();
        for (String cla : conf.getClasses())
            classes.add(cla);

        assertEquals(1, classes.size());
        assertTrue(classes.contains("java.lang.String"));
    }

    @Test
    public void testRecus() {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f private -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        assertTrue(conf.isRecursive());

        args = "-e exepath -d outdir -o outfile -x extension -f private -n 10 me".split(" ");

        com = new CommandLineParser(args);

        conf = com.create();

        assertTrue(!conf.isRecursive());
    }

    @Test
    public void testPrivate() {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f private -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        assertTrue(conf.getMethodFilters().filter(Modifier.PRIVATE));
        assertTrue(conf.getMethodFilters().filter(Modifier.PROTECTED));
        assertTrue(conf.getMethodFilters().filter(Modifier.PUBLIC));
    }

    @Test
    public void testProtected() {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f protected -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        assertFalse(conf.getMethodFilters().filter(Modifier.PRIVATE));
        assertTrue(conf.getMethodFilters().filter(Modifier.PROTECTED));
        assertTrue(conf.getMethodFilters().filter(Modifier.PUBLIC));
    }

    @Test
    public void testDefault() {
        String[] args = "-e exepath -d outdir -o outfile -x extension -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        assertTrue(conf.getMethodFilters().filter(Modifier.PRIVATE));
        assertTrue(conf.getMethodFilters().filter(Modifier.PROTECTED));
        assertTrue(conf.getMethodFilters().filter(Modifier.PUBLIC));
    }

    @Test
    public void testRankDirDefault() {
        String[] args = "-e exepath -d outdir -o outfile -x extension -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        assertEquals("BT", conf.getRankDir());
    }

    @Test
    public void TestJComponentArgument() {
        String[] args = new String[]{"-e", "dot", "-r", "-d", "output", "-o", "Jcomponent", "--filters", "public",
                "-x", "png", "javax.swing.JComponent"};

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();
        IFilter<Modifier> actual = conf.getMethodFilters();
        // expect = new ArrayList<>(Arrays.asList(Modifier.PRIVATE,
        // Modifier.PROTECTED));
        assertTrue(actual.filter(Modifier.PUBLIC));
        assertTrue(actual.filter(Modifier.DEFAULT));
        assertFalse(actual.filter(Modifier.PRIVATE));
        assertFalse(actual.filter(Modifier.PROTECTED));
    }
}
