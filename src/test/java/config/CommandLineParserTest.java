package config;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandLineParserTest {

	@Test
	public void test() {
		String[] args = "-e exepath -d outdir -o outfile -x extension -f private,protected,public -n 10 -r -c me".split(" ");
		
		CommandLineParser com = new CommandLineParser(args);
		
		Configuration conf = com.create();
		
		assertTrue(conf.isNoPublic());
		assertTrue(conf.isNoPrivate());
		assertTrue(conf.isNoProtected());
		assertTrue(conf.isRecursive());
		for(String cla: conf.getClasses())
			assertEquals(cla,"me");
		assertEquals(conf.getExecutablePath(),"exepath");
		assertEquals(conf.getFileName(),"outfile");
		assertEquals(conf.getOutputDirectory(), "outdir");
		assertEquals(Math.round(conf.getNodeSep()),10);
		assertEquals(conf.getOutputFormat(),"extension");
	}

}
