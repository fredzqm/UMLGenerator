package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.java.generator.GraphVizGeneratorTest;
import test.java.model.ASMParserTest;

/**
 * Created by lamd on 12/12/2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        GraphVizGeneratorTest.class,
        ASMParserTest.class,
})
public class AllTests {

}