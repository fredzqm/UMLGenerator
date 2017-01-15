package app;

import analyzer.ClassPair;
import analyzer.IRelationInfo;
import analyzer.ISystemModel;
import config.Configuration;
import config.GeneratorConfiguration;
import config.ModelConfiguration;
import config.RunnerConfiguration;
import display.Viewer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A Test Class that will generate files for local inspection.
 * <p>
 * Created by lamd on 12/15/2016.
 */
public class GeneratePlantUMLScript {
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

        String[] toAnalyze = new String[]{"analyzer.ClassPair", "analyzer.IAnalyzer", "analyzer.IAnalyzerConfiguration", "analyzer.IClassModel", "analyzer.IClassModelFilter", "analyzer.IFieldModel", "analyzer.IMethodModel", "analyzer.IRelationInfo", "analyzer.ISystemModel", "analyzer.ISystemModelFilter", "analyzer.ITypeModel", "analyzer.Relation", "analyzerClassParser.AnalyzerClassParser", "analyzerClassParser.GraphVizClass", "analyzerClassParser.GraphVizFieldParser", "analyzerClassParser.GraphVizHeaderParser", "analyzerClassParser.GraphVizMethodParser", "analyzerClassParser.GraphVizModifierParser", "analyzerClassParser.GraphVizTypeParser", "analyzerClassParser.IClassParserConfiguration", "analyzerClassParser.IParser", "analyzerClassParser.ParseClassSystemModel", "analyzerRelationParser.AnalyzerRelationParser", "analyzerRelationParser.MergeRelationSystemModel", "analyzerRelationParser.ParseRelationSystemModel", "analyzerRelationParser.RelationBijectiveDecorator", "analyzerRelationParser.RelationDependsOn", "analyzerRelationParser.RelationExtendsClass", "analyzerRelationParser.RelationHasA", "analyzerRelationParser.RelationHasABijective", "analyzerRelationParser.RelationImplement", "app.AbstractUMLEngine", "app.Application", "app.UMLEngine", "config.AnalyzerConfiguration", "config.ClassParserConfiguration", "config.CommandLineFileInput", "config.CommandLineParser", "config.ConfigFileParser", "config.Configurable", "config.Configuration", "config.ConfigurationFactory", "config.GeneratorConfiguration", "config.IConfiguration", "config.ModelConfiguration", "config.RunnerConfiguration", "display.Display", "generator.GraphVizGenerator", "generator.IEdge", "generator.IGenerator", "generator.IGeneratorConfiguration", "generator.IGraph", "generator.INode", "model.ArrayTypeModel", "model.ASMParser", "model.ClassModel", "model.FieldModel", "model.GenericTypeArg", "model.GenericTypeParam", "model.GenericTypeVarPlaceHolder", "model.IModelConfiguration", "model.MethodModel", "model.ParametizedClassModel", "model.PrimitiveType", "model.Signature", "model.SystemModel", "model.TypeModel", "model.TypeParser", "runner.GraphVizRunner", "runner.IRunner", "runner.IRunnerConfiguration", "utility.ClassType", "utility.ExpandIterator", "utility.FilteredIterator", "utility.IExpander", "utility.IFilter", "utility.IMapper", "utility.MappedIterator", "utility.MethodType", "utility.Modifier"};
        // String[] toAnalyze = {"model.ASMParser","model.ClassModel"};
        Arrays.stream(toAnalyze).forEach(s -> config.add(ModelConfiguration.CLASSES_KEY, s));

        UMLEngine engine = UMLEngine.getInstance(config);
        ISystemModel systemModel = engine.createSystemModel();
        systemModel = engine.analyze(systemModel);
        Set<String> strings = new HashSet<String>();
        System.out.println(systemModel.getRelations());
        for (ClassPair c : systemModel.getRelations().keySet()) {
            String[] mclasses = c.toString().split(" -> ");
//        	System.out.println(mclasses[0].split("\\.")[1]);
//        	System.out.println(mclasses[1].split("\\.")[1]);
            for (IRelationInfo r : systemModel.getRelations().get(c)) {
                String arrow = r.toString();
                String style = r.getEdgeStyle();
                if (arrow.equals("Implements"))
                    System.out.println(mclasses[1].split("\\.")[1] + " <|. " + mclasses[0].split("\\.")[1]);
                else if (arrow.equals("has many 0..n"))
                    System.out.println(mclasses[1].split("\\.")[1] + " <-\"0..n\" " + mclasses[0].split("\\.")[1]);
                else if (arrow.equals("has many 1..n"))
                    System.out.println(mclasses[1].split("\\.")[1] + " <-\"1..n\" " + mclasses[0].split("\\.")[1]);
                else if (arrow.equals("bir-Depends on many")) {
                    System.out.println(mclasses[1].split("\\.")[1] + " <..>\"1..n\" " + mclasses[0].split("\\.")[1]);
                } else if (arrow.trim().equals("bir-Depends on"))
                    System.out.println(mclasses[1].split("\\.")[1] + " <..> " + mclasses[0].split("\\.")[1]);
                else if (arrow.equals("has many 2..n"))
                    System.out.println(mclasses[1].split("\\.")[1] + " <-\"2..n\" " + mclasses[0].split("\\.")[1]);
                else if (arrow.equals("has 2"))
                    System.out.println(mclasses[1].split("\\.")[1] + " <- " + mclasses[0].split("\\.")[1]);
                else if (arrow.equals("has 1"))
                    System.out.println(mclasses[1].split("\\.")[1] + " <- " + mclasses[0].split("\\.")[1]);
                else if (arrow.equals("Extends"))
                    System.out.println(mclasses[1].split("\\.")[1] + " <|- " + mclasses[0].split("\\.")[1]);
                else if (arrow.equals("Depends on many"))
                    System.out.println(mclasses[1].split("\\.")[1] + " <.\"0..n\" " + mclasses[0].split("\\.")[1]);
                else if (arrow.trim().equals("Depends on"))
                    System.out.println(mclasses[1].split("\\.")[1] + " <. " + mclasses[0].split("\\.")[1]);
                else
                    strings.add(arrow);
            }
        }
        System.out.println(strings);
        String actual = engine.generate(systemModel);
        engine.executeRunner(actual);
//        engine.run();

        config.set(RunnerConfiguration.EXECUTABLE_PATH, "explorer");
        Runnable viewer = new Viewer(RunnerConfiguration.class.cast(config.createConfiguration(RunnerConfiguration.class)));
        viewer.run();

        System.out.println("Done");
    }

}
