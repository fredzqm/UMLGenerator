package config;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.Modifier;

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
		for(String cla: conf.getClasses())
			assertEquals("me",cla);
		assertEquals("exepath",conf.getExecutablePath());
		assertEquals("outfile",conf.getFileName());
		assertEquals("outdir",conf.getOutputDirectory());
		assertEquals(10,Math.round(conf.getNodeSep()));
		assertEquals("extension",conf.getOutputFormat());
		assertEquals("TB",conf.getRankDir());
	}
	
	@Test
	public void testCommandAllLongFlags() {
		String[] args = "--executable exepath --directory outdir --outputfile outfile --extension extension --filters public --direction --nodesep 10 --recursive me".split(" ");
		
		CommandLineParser com = new CommandLineParser(args);
		
		Configuration conf = com.create();
		
		assertTrue(conf.getFilters().contains(Modifier.PRIVATE));
		assertTrue(conf.getFilters().contains(Modifier.PROTECTED));
		assertTrue(!conf.getFilters().contains(Modifier.PUBLIC));
		assertTrue(conf.isRecursive());
		for(String cla: conf.getClasses())
			assertEquals("me",cla);
		assertEquals("exepath",conf.getExecutablePath());
		assertEquals("outfile",conf.getFileName());
		assertEquals("outdir",conf.getOutputDirectory());
		assertEquals(10,Math.round(conf.getNodeSep()));
		assertEquals("extension",conf.getOutputFormat());
		assertEquals("TB",conf.getRankDir());
	}
	
	@Test
	public void testNodeSep() {
		String[] args = "-e exepath -d outdir -o outfile -x extension -f public -n 10.1 -r me".split(" ");
		
		CommandLineParser com = new CommandLineParser(args);
		
		Configuration conf = com.create();
		
		assertEquals(101,Math.round(10*conf.getNodeSep()));
	}	
	
	@Test
	public void testMultipleClasses() {
		String[] args = "-e exepath -d outdir -o outfile -x extension -f private -n 10 -r me you us".split(" ");
		
		CommandLineParser com = new CommandLineParser(args);
		
		Configuration conf = com.create();
		
		ArrayList<String> classes = new ArrayList<String>();
		for(String cla: conf.getClasses())
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
		for(String cla: conf.getClasses())
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
		
		assertEquals("BT",conf.getRankDir());
	}
	
	@Test
	public void testToString() {
		String[] args = "-e exepath -d outdir -o outfile -x extension -f public -k -n 10 -r me".split(" ");
		
		CommandLineParser com = new CommandLineParser(args);
		
		Configuration conf = com.create();
		
		String out = "Classes:                   [me]\n"
				+ "Executable Path:           exepath\n"
				+ "Output Extension:          extension\n"
				+ "Output Directory:          outdir\n"
				+ "Output file name:          outfile\n"
				+ "Node seperation value:     10.0\n"
				+ "Filters:                   [PRIVATE, PROTECTED]\n"
				+ "Recursive?:                true\n"
				+ "Rank Dir:                  TB";
		
		assertEquals(out, conf.toString());
	}

}
