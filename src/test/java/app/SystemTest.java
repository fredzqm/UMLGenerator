package app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import analyzer.ClassPair;
import analyzer.IClassModel;
import analyzer.IRelationInfo;
import analyzer.ISystemModel;
import analyzer.Relation;
import analyzerRelationParser.RelationDependsOn;
import analyzerRelationParser.RelationExtendsClass;
import analyzerRelationParser.RelationImplement;
import config.Configuration;
import dummy.DummyInterface;
import dummy.DummySubClass;
import dummy.DummySuperClass;
import dummy.GenDummyClass;
import dummy.RelDummyClass;
import dummy.RelDummyManyClass;
import dummy.RelOtherDummyClass;
import utility.Modifier;

/**
 * The GraphVizGenerator and GraphVizRunner Test.
 * <p>
 * Created by lamd on 12/11/2016.
 */
public class SystemTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private String dummyClassName = GenDummyClass.class.getPackage().getName() + "."
            + GenDummyClass.class.getSimpleName();

    @Test
    public void graphVizGenerate() throws IOException {
        // Set up the config.
        Configuration config = Configuration.getInstance();
        config.setClasses(Arrays.asList(dummyClassName));
        config.setRecursive(true);
        config.setNodesep(1.0);
        config.setRankDir("BT");

        AbstractUMLEngine engine = UMLEngine.getInstance(config);
        ISystemModel systemModel = engine.createSystemModel();
        systemModel = engine.analyze(systemModel);
        String actual = engine.generate(systemModel);

        // Test if it has the basic DOT file styling.
        assertTrue("Missing nodesep.", actual.contains("nodesep=1.0;"));
        assertTrue("Missing node shape.", actual.contains("node [shape=record];"));
        assertTrue("Missing rankdir.", actual.contains("rankdir=BT"));
        assertTrue("Missing primary class name.", actual.contains(String.format("\"%s\"", dummyClassName)));

        // See if it has its expected super class.
        String expectedSuperClass = String.format("\"%s\" -> \"java.lang.Object\" [arrowhead=\"onormal\" style=\"\" ];",
                dummyClassName);
        assertTrue("Missing super class relation.", actual.contains(expectedSuperClass));

        // See if it has its expected dependencies.
        String expectedDependencies = String.format(
                "\"%s\" -> \"java.lang.String\" [arrowhead=\"vee\" style=\"\" taillabel=\"1..*\" ];", dummyClassName);
        assertTrue("Missing dependency relations.", actual.contains(expectedDependencies));

        // Check expected fields and methods.
        String[] expectedFields = { "- privateInt : int", "+ publicString : java.lang.String",
                "- privateString : java.lang.String", "+ publicInt : int" };
        String[] expectedMethods = { "- printPrivateString() : void", "getPublicInt() : int",
                "+ getPublicString() : java.lang.String", "# someProtectedMethod() : double" };

        Stream<String> expectedFieldStream = Arrays.stream(expectedFields);
        Stream<String> expectedMethodStream = Arrays.stream(expectedMethods);

        // Test if it has the Fields viewable in the class file.
        expectedFieldStream.forEach(
                (field) -> assertTrue(String.format("Missing expected field: %s", field), actual.contains(field)));

        // Test if it has the Methods viewable in the class file.
        expectedMethodStream.forEach(
                (method) -> assertTrue(String.format("Missing expected method: %s", method), actual.contains(method)));
    }

    @Test
    public void graphVizGeneratorFilter() {
        // Set up the config.
        Configuration config = Configuration.getInstance();
        config.setClasses(Arrays.asList(dummyClassName));
        config.setFilters(data -> data == Modifier.DEFAULT || data == Modifier.PUBLIC);
        config.setNodesep(1.0);
        config.setRecursive(true);
        config.setRankDir("BT");

        // Set up the system model and generator.
        AbstractUMLEngine engine = UMLEngine.getInstance(config);
        ISystemModel systemModel = engine.createSystemModel();
        systemModel = engine.analyze(systemModel);
        String actual = engine.generate(systemModel);

        // Test if it has the basic DOT file styling.
        assertTrue("Missing nodesep.", actual.contains("nodesep=1.0;"));
        assertTrue("Missing node shape.", actual.contains("node [shape=record];"));
        assertTrue("Missing rankdir.", actual.contains("rankdir=BT"));
        assertTrue("Missing primary class name.", actual.contains(String.format("\"%s\"", dummyClassName)));

        // See if it has its expected super class.
        String expectedSuperClass = String.format("\"%s\" -> \"java.lang.Object\" [arrowhead=\"onormal\" style=\"\" ];",
                dummyClassName);
        assertTrue("Missing super class relation.", actual.contains(expectedSuperClass));

        // See if it has its expected dependencies.
        String expectedDependencies = String.format(
                "\"%s\" -> \"java.lang.String\" [arrowhead=\"vee\" style=\"\" taillabel=\"1..*\" ];", dummyClassName);
        assertTrue("Missing dependency relations.", actual.contains(expectedDependencies));

        // Set up expected fields and methods.
        String[] expectedFields = { "+ publicString : java.lang.String", "+ publicInt : int" };
        String[] expectedMethods = { "getPublicInt() : int", "+ getPublicString() : java.lang.String" };
        Stream<String> expectedFieldStream = Arrays.stream(expectedFields);
        Stream<String> expectedMethodStream = Arrays.stream(expectedMethods);

        // Test if it has the Fields viewable in the class file.
        expectedFieldStream.forEach(
                (field) -> assertTrue(String.format("Missing expected field: %s", field), actual.contains(field)));

        // Test if it has the Methods viewable in the class file.
        expectedMethodStream.forEach(
                (method) -> assertTrue(String.format("Missing expected method: %s", method), actual.contains(method)));
    }

    @Test
    public void graphVizWrite() throws IOException {
        // Create a TemporaryFolder that will be deleted after the test runs.
        File directory = this.folder.newFolder("testDirectory");

        // Set up config.
        Configuration config = Configuration.getInstance();
        config.setClasses(Arrays.asList(dummyClassName));
        config.setRecursive(false);
        config.setFileName("testWrite");
        config.setOutputFormat("svg");
        config.setExecutablePath("dot");
        config.setRankDir("BT");
        config.setOutputDirectory(directory.toString());

        // Set up a System Model.
        AbstractUMLEngine engine = UMLEngine.getInstance(config);
        ISystemModel systemModel = engine.createSystemModel();
        systemModel = engine.analyze(systemModel);
        String graphVizString = engine.generate(systemModel);
        engine.executeRunner(graphVizString);
    }

    @Test
    public void testOverImplementing() throws IOException {
        // Create a TemporaryFolder that will be deleted after the test runs.
        File directory = this.folder.newFolder("testDirectory");

        // Set up config.
        Configuration config = Configuration.getInstance();
        config.setClasses(Arrays.asList(DummyInterface.class.getName(), DummySubClass.class.getName(),
                DummySuperClass.class.getName()));
        config.setRecursive(false);
        config.setFileName("testImplements");
        config.setOutputDirectory(directory.toString());

        // Set up a System Model.
        AbstractUMLEngine engine = UMLEngine.getInstance(config);
        ISystemModel systemModel = engine.createSystemModel();
        systemModel = engine.analyze(systemModel);
        
        // get classes
        Collection<? extends IClassModel> classes = systemModel.getClasses();
        IClassModel dummyInterface = getClassFromCollection(DummyInterface.class.getName(), classes);
        IClassModel dummyStub = getClassFromCollection(DummySubClass.class.getName(), classes);
        IClassModel dummySuperClass = getClassFromCollection(DummySuperClass.class.getName(), classes);
        
        // get relations
        Map<ClassPair, List<IRelationInfo>> relations = systemModel.getRelations();
        List<IRelationInfo> relFromStubToSuperClass = relations.get(new ClassPair(dummyStub, dummySuperClass));
        assertEquals(Arrays.asList(new RelationExtendsClass()), relFromStubToSuperClass);

        List<IRelationInfo> relFromStubToInterface = relations.get(new ClassPair(dummyStub, dummyInterface));
        assertNull(relFromStubToInterface);

        List<IRelationInfo> relFromSuperToInterfaceClass = relations
                .get(new ClassPair(dummySuperClass, dummyInterface));
        assertEquals(Arrays.asList(new RelationImplement()), relFromSuperToInterfaceClass);
    }

    private IClassModel getClassFromCollection(String name, Collection<? extends IClassModel> classes) {
        for (IClassModel c : classes) {
            if (c.getName().equals(name))
                return c;
        }
        fail("Class " + name + " does not exist");
        return null;
    }

    @Test
    public void graphVizManyNoFields() {
        // Set up config.
        Configuration config = Configuration.getInstance();
        config.setFilters(data -> data == Modifier.DEFAULT || data == Modifier.PUBLIC);
        config.setRecursive(true);
        List<String> classList = new ArrayList<>();
        classList.add(RelDummyManyClass.class.getName());
        classList.add(RelOtherDummyClass.class.getName());
        classList.add(RelDummyClass.class.getName());
        config.setClasses(classList);

        // Set up SystemModel and Generator.
        AbstractUMLEngine engine = UMLEngine.getInstance(config);
        ISystemModel systemModel = engine.createSystemModel();

        // get classes
        Collection<? extends IClassModel> classes = systemModel.getClasses();
        IClassModel RelDummyManyClass = getClassFromCollection(RelDummyManyClass.class.getName(), classes);
        IClassModel RelOtherDummyClass = getClassFromCollection(RelOtherDummyClass.class.getName(), classes);
        IClassModel RelDummyClass = getClassFromCollection(RelDummyClass.class.getName(), classes);
        
        // get relations.
        Map<ClassPair, List<IRelationInfo>> relations = systemModel.getRelations();
        List<IRelationInfo> relFromManyToOther = relations.get(new ClassPair(RelDummyManyClass, RelOtherDummyClass));
        assertTrue("Missing expected array dependency", relFromManyToOther.contains(new RelationDependsOn(0)));
        
        List<IRelationInfo> relFromOtherToRel = relations.get(new ClassPair(RelDummyManyClass, RelDummyClass));
        assertTrue("Missing expected generic dependency", relFromOtherToRel.contains(new RelationDependsOn(0)));

        String actual = engine.generate(systemModel);
        String expectedDependencyCardinality = "\"dummy.RelDummyManyClass\" ->"
                + "\"dummy.RelOtherDummyClass\" [arrowhead=\"vee\" style=\"dashed\"" + "headlabel=\"1..*\" ];";
        assertTrue("Missing GraphViz dependency", actual.contains(expectedDependencyCardinality));
    }

}