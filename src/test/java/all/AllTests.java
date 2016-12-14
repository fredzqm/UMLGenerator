package all;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import config.CommandLineParserTest;
import generator.GraphVizGeneratorTest;
import model.ASMParserTest;

/**
 * Runs all test suite.
 *
 * Created by lamd on 12/12/2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        GraphVizGeneratorTest.class,
        ASMParserTest.class,
        CommandLineParserTest.class
})
public class AllTests {

}