package analyzer.favorComposition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import analyzer.utility.IAnalyzer;
import analyzer.utility.IClassModel;
import analyzer.utility.ISystemModel;
import utility.ClassType;

/**
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

        IClassModel concreteClassModel = mock(IClassModel.class, concreteSuperClass);
        when(concreteClassModel.getName()).thenReturn(concreteSuperClass);
        when(concreteClassModel.getType()).thenReturn(ClassType.CONCRETE);

        IClassModel noCompositionClassModel = mock(IClassModel.class, noComposition);
        when(noCompositionClassModel.getName()).thenReturn(noComposition);
        when(noCompositionClassModel.getSuperClass()).thenReturn(concreteClassModel);
        when(noCompositionClassModel.getNodeStyle()).thenReturn("");

        IClassModel compositionClassModel = mock(IClassModel.class, composition);
        when(compositionClassModel.getName()).thenReturn(composition);
        when(compositionClassModel.getSuperClass()).thenReturn(objectClassModel);
        when(compositionClassModel.getNodeStyle()).thenReturn("");

        // create systemModel
        ISystemModel systemModelMock = mock(ISystemModel.class);
        doReturn(Arrays.asList(compositionClassModel, noCompositionClassModel)).when(systemModelMock).getClasses();

        // start
        ISystemModel systemModel = runAnalyzer(systemModelMock);

        // get decorated classModels
        Collection<? extends IClassModel> classList = systemModel.getClasses();
        assertEquals(2, classList.size());

        IClassModel favor = getClassFromIterableByName(composition, classList);
        IClassModel noFavor = getClassFromIterableByName(noComposition, classList);

        assertEquals("This should not have a node style as it does not break the principle: Favor Composition over Inheritance.", "", favor.getNodeStyle());
        assertEquals("This should have a node style as it does break the principle: Favor Composition over Inheritance.", "color=\"orange\" ", noFavor.getNodeStyle());


        // verify
        verify(noCompositionClassModel).getSuperClass();
    }

    private ISystemModel runAnalyzer(ISystemModel systemModelMock) {
        IAnalyzer analyzer = new FavorCompositionAnalyzer();
        return analyzer.analyze(systemModelMock, null);
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