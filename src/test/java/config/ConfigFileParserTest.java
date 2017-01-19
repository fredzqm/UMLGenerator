package config;

import org.junit.Test;

import utility.IFilter;
import utility.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfigFileParserTest {

    @Test
    public void parseTest() throws Exception {
        String args = "";
        CommandLineFileInput com = new CommandLineFileInput(args);
        ConfigFileParser parser = new ConfigFileParser(com.getJson());

        Configuration config = parser.create();

        assertEquals("java.lang.String", config.getList(ModelConfiguration.CLASSES_KEY).iterator().next());
        assertEquals("dot", config.getValue(RunnerConfiguration.EXECUTABLE_PATH));
        assertEquals("svg", config.getValue(RunnerConfiguration.OUTPUT_FORMAT));
        assertEquals("output", config.getValue(RunnerConfiguration.OUTPUT_DIRECTORY));
        assertEquals(10, (int) (10 * Double.parseDouble(config.getValue(GeneratorConfiguration.NODE_SEP))));
        assertTrue(!Boolean.parseBoolean(config.getValue(ModelConfiguration.IS_RECURSIVE_KEY)));

        assertEquals(ClassParserConfiguration.MODIFIER_FILTER_PUBLIC, config.getValue(ClassParserConfiguration.MODIFIER_FILTER));

        assertEquals("BT", config.getValue(GeneratorConfiguration.RANK_DIR));
    }

}
