package all;

import config.CommandLineParserTest;
import generator.GraphVizGeneratorSystemTest;
import model.ASMParserTest;
import model.ClassModelRelationshipTest;
import model.ClassModelTest;
import model.MethodModelTest;
import model.SystemModelTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import utility.IFilterTest;

/**
 * Runs all test suite.
 * <p>
 * Created by lamd on 12/12/2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({GraphVizGeneratorSystemTest.class, ASMParserTest.class, CommandLineParserTest.class,
        SystemModelTest.class, ClassModelTest.class, IFilterTest.class, MethodModelTest.class, ClassModelRelationshipTest.class})
public class AllTests {

}