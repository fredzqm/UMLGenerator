package all;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import config.CommandLineParserTest;
import generator.GraphVizGeneratorTest;
import model.ASMParserTest;
import model.ClassModelTest;
import model.SystemModelTest;
import util.IFilterTest;

/**
 * Runs all test suite.
 *
 * Created by lamd on 12/12/2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ GraphVizGeneratorTest.class, ASMParserTest.class, CommandLineParserTest.class,
		SystemModelTest.class, ClassModelTest.class, IFilterTest.class})
public class AllTests {

}