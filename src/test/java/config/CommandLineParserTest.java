package config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.util.Collections;

import org.junit.Test;

import analyzer.classParser.ClassParserConfiguration;
import generator.GeneratorConfiguration;
import model.ModelConfiguration;
import runner.RunnerConfiguration;
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
        
        ModelConfiguration modelConfig = parser.createConfiguration(ModelConfiguration.class);
        assertFalse(modelConfig.isRecursive());
        assertEquals(Collections.singletonList("java.lang.String"), modelConfig.getClasses());
        
        RunnerConfiguration runnerConfig = parser.createConfiguration(RunnerConfiguration.class);
        assertEquals("dot", runnerConfig.getExecutablePath());
        assertEquals("svg", runnerConfig.getOutputFormat());
        assertEquals("out", runnerConfig.getFileName());
        
        GeneratorConfiguration generatorConfig = parser.createConfiguration(GeneratorConfiguration.class);
        assertEquals(1.0, generatorConfig.getNodeSep(), 0.000000001);
        assertEquals("BT", generatorConfig.getRankDir());
        
        ClassParserConfiguration classParserConfig = parser.createConfiguration(ClassParserConfiguration.class);
        IFilter<Modifier> filter = classParserConfig.getModifierFilters();
        assertTrue(filter.filter(Modifier.PUBLIC));
        assertFalse(filter.filter(Modifier.DEFAULT));
        assertFalse(filter.filter(Modifier.PROTECTED));
        assertFalse(filter.filter(Modifier.PRIVATE));
    }

}
