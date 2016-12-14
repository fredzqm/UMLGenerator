package config;

import model.Modifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandLineParserTest {

    @Test
    public void test() {
        String[] args = "-e exepath -d outdir -o outfile -x extension -f public -n 10 -r me".split(" ");

        CommandLineParser com = new CommandLineParser(args);

        Configuration conf = com.create();

        assertTrue(conf.getFilters().contains(Modifier.PRIVATE));
        assertTrue(conf.getFilters().contains(Modifier.PROTECTED));
        assertTrue(!conf.getFilters().contains(Modifier.PUBLIC));
        assertTrue(conf.isRecursive());
        for (String cla : conf.getClasses())
            assertEquals(cla, "me");
        assertEquals(conf.getExecutablePath(), "exepath");
        assertEquals(conf.getFileName(), "outfile");
        assertEquals(conf.getOutputDirectory(), "outdir");
        assertEquals(Math.round(conf.getNodeSep()), 10);
        assertEquals(conf.getOutputFormat(), "extension");
    }

}
