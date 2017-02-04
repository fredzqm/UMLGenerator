package analyzer.singleton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import analyzer.utility.IAnalyzer;
import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;
import analyzer.utility.ISystemModel;
import analyzer.utility.ITypeModel;
import config.Configuration;
import config.IConfiguration;
import utility.Modifier;

/**
 * Created by lamd on 1/15/2017.
 */
public class SingletonAnalyzerTest {

    @Test
    public void analyze() throws Exception {
        // mock creation
        String singletonName = "singleton";
        String singletonStaticFieldName = "staticField";
        String staticGetInstanceMethod = "getInstance";

        IClassModel _singletonClassModel = mock(IClassModel.class, singletonName);
        IFieldModel _staticSingletonField = mock(IFieldModel.class, singletonStaticFieldName);
        IMethodModel _getInstanceMethod = mock(IMethodModel.class, staticGetInstanceMethod);
        ITypeModel _singletonTypeModel = mock(ITypeModel.class, singletonName);

        // specify type model behavior
        when(_singletonTypeModel.getClassModel()).thenReturn(_singletonClassModel);
        // specify class model behavior
        when(_singletonClassModel.getName()).thenReturn(singletonName);
        when(_singletonClassModel.getNodeStyle()).thenReturn("");
        when(_singletonClassModel.getUnderlyingClassModel()).thenReturn(_singletonClassModel);
        doReturn(Collections.singletonList(_staticSingletonField)).when(_singletonClassModel).getFields();
        doReturn(Collections.singletonList(_getInstanceMethod)).when(_singletonClassModel).getMethods();
        // specify class field model behavior
        when(_staticSingletonField.isStatic()).thenReturn(true);
        when(_staticSingletonField.getModifier()).thenReturn(Modifier.PRIVATE);
        when(_staticSingletonField.getFieldType()).thenReturn(_singletonTypeModel);
        // specify class method model behavior
        doReturn(Collections.singletonList(_staticSingletonField)).when(_getInstanceMethod).getAccessedFields();
        when(_getInstanceMethod.getModifier()).thenReturn(Modifier.PUBLIC);
        when(_getInstanceMethod.getReturnType()).thenReturn(_singletonTypeModel);
        when(_getInstanceMethod.isStatic()).thenReturn(true);

        // create systemModel
        ISystemModel _systemModelMock = mock(ISystemModel.class);
        doReturn(new HashSet<>(Collections.singletonList(_singletonClassModel))).when(_systemModelMock).getClasses();

        // start
        ISystemModel systemModel = runAnalyzer(_systemModelMock);

        // get decorated classModels
        Set<? extends IClassModel> classList = systemModel.getClasses();
        assertEquals(1, classList.size());

        IClassModel singletonClassModel = getClassFromIterableByName(singletonName, classList);

        Collection<String> stereoTypes = singletonClassModel.getStereoTypes();
        assertTrue("The stereotypes is not added", stereoTypes.contains("Singleton"));
        assertEquals(" color=\"blue\"", singletonClassModel.getNodeStyle());
    }

    private ISystemModel runAnalyzer(ISystemModel systemModelMock) {
        IAnalyzer analyzer = new SingletonAnalyzer();
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