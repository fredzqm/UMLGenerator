package app;

import java.io.IOException;
import java.util.Arrays;

import analyzer.ISystemModel;
import config.Configuration;
import config.GeneratorConfiguration;
import config.ModelConfiguration;
import config.RunnerConfiguration;
import runner.ExplorerRunner;
import runner.IRunner;

/**
 * A Test Class that will generate files for local inspection.
 * <p>
 * Created by lamd on 12/15/2016.
 */
public class LocalTester {
    public static void main(String[] args) {
        // Set up the config.
        Configuration config = Configuration.getInstance();
//        config.add(ModelConfiguration.CLASSES_KEY, Dummy.class);
//        config.add(ModelConfiguration.CLASSES_KEY, RelDummyManyClass.class);
//        config.add(ModelConfiguration.CLASSES_KEY, RelOtherDummyClass.class);
//        config.add(ModelConfiguration.CLASSES_KEY, RelDummyClass.class);
        config.set(ModelConfiguration.IS_RECURSIVE_KEY, Boolean.toString(false));

        config.set(GeneratorConfiguration.NODE_SEP, Double.toString(1.0));
        config.set(GeneratorConfiguration.RANK_DIR, ("BT"));

        config.set(RunnerConfiguration.OUTPUT_DIRECTORY, "./output");
        config.set(RunnerConfiguration.FILE_NAME, "asmClass");
        config.set(RunnerConfiguration.EXECUTABLE_PATH, "dot");
        config.set(RunnerConfiguration.OUTPUT_FORMAT, "svg");

        //String[] toAnalyze = new String[]{"analyzer.ClassPair", "analyzer.IAnalyzer", "analyzer.IAnalyzerConfiguration", "analyzer.IClassModel", "analyzer.IClassModelFilter", "analyzer.IFieldModel", "analyzer.IMethodModel", "analyzer.IRelationInfo", "analyzer.ISystemModel", "analyzer.ISystemModelFilter", "analyzer.ITypeModel", "analyzer.Relation", "analyzerClassParser.AnalyzerClassParser", "analyzerClassParser.GraphVizClass", "analyzerClassParser.GraphVizFieldParser", "analyzerClassParser.GraphVizHeaderParser", "analyzerClassParser.GraphVizMethodParser", "analyzerClassParser.GraphVizModifierParser", "analyzerClassParser.GraphVizTypeParser", "analyzerClassParser.IClassParserConfiguration", "analyzerClassParser.IParser", "analyzerClassParser.ParseClassSystemModel", "analyzerRelationParser.AnalyzerRelationParser", "analyzerRelationParser.MergeRelationSystemModel", "analyzerRelationParser.ParseRelationSystemModel", "analyzerRelationParser.RelationBijectiveDecorator", "analyzerRelationParser.RelationDependsOn", "analyzerRelationParser.RelationExtendsClass", "analyzerRelationParser.RelationHasA", "analyzerRelationParser.RelationHasABijective", "analyzerRelationParser.RelationImplement", "app.AbstractUMLEngine", "app.Application", "app.UMLEngine", "config.AnalyzerConfiguration", "config.ClassParserConfiguration", "config.CommandLineFileInput", "config.CommandLineParser", "config.ConfigFileParser", "config.Configurable", "config.Configuration", "config.ConfigurationFactory", "config.GeneratorConfiguration", "config.IConfiguration", "config.ModelConfiguration", "config.RunnerConfiguration", "display.Display", "generator.GraphVizGenerator", "generator.IEdge", "generator.IGenerator", "generator.IGeneratorConfiguration", "generator.IGraph", "generator.IVertex", "model.ArrayTypeModel", "model.ASMParser", "model.ClassModel", "model.FieldModel", "model.GenericTypeArg", "model.GenericTypeParam", "model.GenericTypeVarPlaceHolder", "model.IModelConfiguration", "model.MethodModel", "model.ParametizedClassModel", "model.PrimitiveType", "model.Signature", "model.SystemModel", "model.TypeModel", "model.TypeParser", "runner.GraphVizRunner", "runner.IRunner", "runner.IRunnerConfiguration", "utility.ClassType", "utility.ExpandIterator", "utility.FilteredIterator", "utility.IExpander", "utility.IFilter", "utility.IMapper", "utility.MappedIterator", "utility.MethodType", "utility.Modifier"};
        String[] toAnalyze = {"model.ASMParser","model.ClassModel"};
        Arrays.stream(toAnalyze).forEach(s -> config.add(ModelConfiguration.CLASSES_KEY, s));

        UMLEngine engine = UMLEngine.getInstance(config);
        ISystemModel systemModel = engine.createSystemModel();
        systemModel = engine.analyze(systemModel);
        
        System.out.println(systemModel.getRelations());
        
        String actual = engine.generate(systemModel);
        engine.executeRunner(actual);
//        engine.run();

        config.set(RunnerConfiguration.EXECUTABLE_PATH, "explorer");
        IRunner explorerRunner = new ExplorerRunner(RunnerConfiguration.class.cast(config.createConfiguration(RunnerConfiguration.class)));
        try {
            explorerRunner.execute(null);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.err.println("Failed to execute ExplorerRunner");
        }

        System.out.println("Done");
    }

}
