package app;

import config.CommandLineParserTest;
import model.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import utility.IFilterTest;

/**
 * Runs all test suite.
 * <p>
 * Created by lamd on 12/12/2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({SystemTest.class, ASMParserTest.class, CommandLineParserTest.class,
        SystemModelTest.class, ClassModelTest.class, IFilterTest.class, MethodModelTest.class,
        ClassModelRelationshipTest.class, TypeParserTest.class, TypeModelTest.class})
public class AllTests {

}