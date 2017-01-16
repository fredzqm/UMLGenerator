package app;

import analyzer.classParser.AnalyzerClassParserTest;
import analyzer.favorComposition.FavorCompositionAnalyzerTest;
import analyzer.relationParser.AnalyzerRelationParserTest;
import config.CommandLineParserTest;
import config.TestNoArgs;
import model.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import utility.IExpanderTest;
import utility.IFilterTest;
import utility.IMapperTest;

/**
 * Runs all test suite.
 * <p>
 * Created by lamd on 12/12/2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({AnalyzerClassParserTest.class, FavorCompositionAnalyzerTest.class, AnalyzerRelationParserTest.class,
        SystemTest.class, CommandLineParserTest.class, CommandLineParserTest.class, TestNoArgs.class, ASMParserTest.class,
        ASMParserTest.class, ClassModelTest.class, MethodModelTest.class, SignatureTest.class, SystemModel.class, TypeModelTest.class,
        TypeParserTest.class, IExpanderTest.class, IFilterTest.class, IMapperTest.class})
public class AllTests {

}