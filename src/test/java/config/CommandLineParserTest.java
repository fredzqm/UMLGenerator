package config;

import generator.IModifier;
import model.Modifier;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandLineParserTest {

    @Test
    public void testCommandAllShortFlags() {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f public -k -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        assertTrue(conf.getFilters().contains(Modifier.PRIVATE));
        assertTrue(conf.getFilters().contains(Modifier.PROTECTED));
        assertTrue(!conf.getFilters().contains(Modifier.PUBLIC));
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

        assertTrue(conf.getFilters().contains(Modifier.PRIVATE));
        assertTrue(conf.getFilters().contains(Modifier.PROTECTED));
        assertTrue(!conf.getFilters().contains(Modifier.PUBLIC));
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
        assertTrue(conf.getFilters().contains(Modifier.PRIVATE));
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

        assertTrue(!conf.getFilters().contains(Modifier.PRIVATE));
        assertTrue(!conf.getFilters().contains(Modifier.PROTECTED));
        assertTrue(!conf.getFilters().contains(Modifier.PUBLIC));
    }

    @Test
    public void testProtected() {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f protected -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        assertTrue(conf.getFilters().contains(Modifier.PRIVATE));
        assertTrue(!conf.getFilters().contains(Modifier.PROTECTED));
        assertTrue(!conf.getFilters().contains(Modifier.PUBLIC));
    }

    @Test
    public void testDefault() {
        String[] args = "-e exepath -d outdir -o outfile -x extension -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        assertTrue(!conf.getFilters().contains(Modifier.PRIVATE));
        assertTrue(!conf.getFilters().contains(Modifier.PROTECTED));
        assertTrue(!conf.getFilters().contains(Modifier.PUBLIC));
    }

    @Test
    public void testRankDirDefault() {
        String[] args = "-e exepath -d outdir -o outfile -x extension -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        assertEquals("BT", conf.getRankDir());
    }

    @Test
    public void testToString() {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f public -k -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        String out = "Classes:                   [me]\n" + "Executable Path:           exepath\n"
                + "Output Extension:          extension\n" + "Output file name:          outfile\n"
                + "Output Directory:          outdir\n" + "Node seperation value:     10.0\n"
                + "Filters:                   [PRIVATE, PROTECTED]\n" + "Recursive?:                true\n"
                + "Rank Dir:                  TB";

        assertEquals(out, conf.toString());
    }

    @Test
    public void TestJComponentArgument() {
        String[] args = new String[]{"-e", "dot", "-r", "-d", "output", "-o", "Jcomponent", "--filters", "public",
                "-x", "png", "javax.swing.JComponent"};

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();
        Collection<IModifier> actual = conf.getFilters();
        Collection<IModifier> expect = new ArrayList<>(Arrays.asList(Modifier.PRIVATE, Modifier.PROTECTED));

        assertEquals(expect, actual);
    }
}
