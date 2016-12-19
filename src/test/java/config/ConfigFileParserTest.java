package config;

import static org.junit.Assert.*;

import org.junit.Test;

import utility.Modifier;

public class ConfigFileParserTest {

	@Test
	public void test() throws Exception {
		String[] args = {""};
		CommandLineFileInput com = new CommandLineFileInput(args);
		ConfigFileParser parser = new ConfigFileParser(com.getJson());

		Configuration config = parser.create();
		
		System.out.println(config.toString());
		assertEquals("java.lang.String",config.getClasses().iterator().next());
		assertEquals("dot", config.getExecutablePath());
		assertEquals("svg", config.getOutputFormat());
		assertEquals("out", config.getFileName());
		assertEquals(10,(int)(10*config.getNodeSep()));
		assertTrue(!config.isRecursive());
		assertTrue(!config.getModifierFilters().filter(Modifier.PRIVATE));
		assertTrue(!config.getModifierFilters().filter(Modifier.PROTECTED));
		assertTrue(config.getModifierFilters().filter(Modifier.PUBLIC));
		assertEquals("BT", config.getRankDir());
	}

}
