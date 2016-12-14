package config;

import model.Modifier;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandLineParserTest {
	@Test
	public void testCommandAll() {
		String[] args = "-e exepath -d outdir -o outfile -x extension -f public -n 10 -r me".split(" ");
		
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
}
