package analyzer.favorComposition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import adapter.relationParser.RelationExtendsClass;
import analyzer.utility.ClassPair;
import analyzer.utility.IAnalyzer;
import analyzer.utility.IClassModel;
import analyzer.utility.IRelationInfo;
import analyzer.utility.ISystemModel;
import config.Configuration;
import config.IConfiguration;
import utility.ClassType;

/**
 * Test the FavorCompositionAnalyzer.
 * <p>
 * Created by lamd on 1/15/2017.
 */
public class FavorCompositionAnalyzerTest {
    @Test
    public void analyze() throws Exception {
        // mock creation
        String noComposition = "ClassA";
        String composition = "ClassB";
        String concreteSuperClass = "ConcreteClass";
        String objectClass = "java.lang.Object";

        IClassModel objectClassModel = mock(IClassModel.class, objectClass);
        when(objectClassModel.getName()).thenReturn(objectClass);
        when(objectClassModel.getUnderlyingClassModel()).thenReturn(objectClassModel);

        IClassModel concreteClassModel = mock(IClassModel.class, concreteSuperClass);
        when(concreteClassModel.getName()).thenReturn(concreteSuperClass);
        when(concreteClassModel.getType()).thenReturn(ClassType.CONCRETE);
        when(concreteClassModel.getUnderlyingClassModel()).thenReturn(concreteClassModel);

        IClassModel noCompositionClassModel = mock(IClassModel.class, noComposition);
        when(noCompositionClassModel.getName()).thenReturn(noComposition);
        when(noCompositionClassModel.getSuperClass()).thenReturn(concreteClassModel);
        when(noCompositionClassModel.getNodeStyle()).thenReturn("");
        when(noCompositionClassModel.getUnderlyingClassModel()).thenReturn(noCompositionClassModel);

        IClassModel compositionClassModel = mock(IClassModel.class, composition);
        when(compositionClassModel.getName()).thenReturn(composition);
        when(compositionClassModel.getSuperClass()).thenReturn(objectClassModel);
        when(compositionClassModel.getNodeStyle()).thenReturn("");
        when(compositionClassModel.getUnderlyingClassModel()).thenReturn(compositionClassModel);

        Map<ClassPair, List<IRelationInfo>> relations = new HashMap<>();
        relations.put(new ClassPair(noCompositionClassModel, concreteClassModel), Collections.singletonList(new RelationExtendsClass()));
        relations.put(new ClassPair(compositionClassModel, objectClassModel), Collections.singletonList(new RelationExtendsClass()));

        // create systemModel
        ISystemModel systemModelMock = mock(ISystemModel.class);
        doReturn(new HashSet<>(Arrays.asList(compositionClassModel, noCompositionClassModel))).when(systemModelMock).getClasses();
        doReturn(relations).when(systemModelMock).getRelations();
        // TODO: Add relations return


        // start
        ISystemModel systemModel = runAnalyzer(systemModelMock);

        // get decorated classModels
        Collection<? extends IClassModel> classList = systemModel.getClasses();
        assertEquals(2, classList.size());

        IClassModel favor = getClassFromIterableByName(composition, classList);
        IClassModel noFavor = getClassFromIterableByName(noComposition, classList);

        assertEquals("This should not have a node style as it does not break the principle: Favor Composition over Inheritance.", "", favor.getNodeStyle());
        assertEquals("This should have a node style as it does break the principle: Favor Composition over Inheritance.", " color=\"orange\"", noFavor.getNodeStyle());


        // verify
        verify(noCompositionClassModel, times(3)).getSuperClass();
    }

    private ISystemModel runAnalyzer(ISystemModel systemModelMock) {
        IAnalyzer analyzer = new FavorCompositionAnalyzer();
        IConfiguration config = Configuration.getInstance();

        return analyzer.analyze(systemModelMock, config);
    }

    private IClassModel getClassFromIterableByName(String _a_name, Iterable<? extends IClassModel> classList) {
        for (IClassModel x : classList) {
            if (x.getName().equals(_a_name))
                return x;
        }
        fail("did not find class " + _a_name);
        return null;
    }

}