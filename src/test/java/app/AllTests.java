package app;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import analyzer.classParser.AnalyzerClassParserTest;
import analyzer.favorComposition.FavorCompositionAnalyzerTest;
import analyzer.relationParser.AnalyzerRelationParserTest;
import analyzer.singleton.SingletonAnalyzerTest;
import config.CommandLineParserTest;
import model.ASMParserTest;
import model.ClassModelTest;
import model.MethodModelTest;
import model.SignatureTest;
import model.SystemModelTest;
import model.TypeModelTest;
import model.TypeParserTest;
import utility.IExpanderTest;
import utility.IFilterTest;
import utility.IMapperTest;

/**
 * Runs all test suite.
 * <p>
 * Created by lamd on 12/12/2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({AnalyzerClassParserTest.class, FavorCompositionAnalyzerTest.class, AnalyzerRelationParserTest.class,SingletonAnalyzerTest.class,
        SystemTest.class, CommandLineParserTest.class, ASMParserTest.class,
        ASMParserTest.class, ClassModelTest.class, MethodModelTest.class, SignatureTest.class, SystemModelTest.class, TypeModelTest.class,
        TypeParserTest.class, IExpanderTest.class, IFilterTest.class, IMapperTest.class})

public class AllTests {

}