package config;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.junit.Test;

import analyzer.classParser.IClassParserConfiguration;
import generator.IGeneratorConfiguration;
import model.IModelConfiguration;
import runner.IRunnerConfiguration;
import utility.IFilter;
import utility.Modifier;

public class CommandLineParserTest {

    private IConfiguration parseParameter(String... args) {
        CommandLineParser parser = new CommandLineParser(args);
        try {
            return parser.create();
        } catch (FileNotFoundException e) {
            fail("" + e);
            return null;
        }
    }

    @Test
    public void testString() {
        IConfiguration parser = parseParameter("config/String.json");
        
        IModelConfiguration modelConfig = parser.createConfiguration(ModelConfiguration.class);
        assertFalse(modelConfig.isRecursive());
        assertEquals(Arrays.asList("java.lang.String"), modelConfig.getClasses());
        
        IRunnerConfiguration runnerConfig = parser.createConfiguration(RunnerConfiguration.class);
        assertEquals("dot", runnerConfig.getExecutablePath());
        assertEquals("svg", runnerConfig.getOutputFormat());
        assertEquals("out", runnerConfig.getFileName());
        
        IGeneratorConfiguration generatorConfig = parser.createConfiguration(GeneratorConfiguration.class);
        assertEquals(1.0, generatorConfig.getNodeSep(), 0.000000001);
        assertEquals("BT", generatorConfig.getRankDir());
        
        IClassParserConfiguration classParserConfig = parser.createConfiguration(ClassParserConfiguration.class);
        IFilter<Modifier> filter = classParserConfig.getModifierFilters();
        assertTrue(filter.filter(Modifier.PUBLIC));
        assertFalse(filter.filter(Modifier.DEFAULT));
        assertFalse(filter.filter(Modifier.PROTECTED));
        assertFalse(filter.filter(Modifier.PRIVATE));
    }

}
