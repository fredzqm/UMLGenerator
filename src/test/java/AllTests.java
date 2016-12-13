import generator.GraphVizGeneratorTest;
import models.ASMParserTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

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