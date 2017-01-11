package analyzerRelationParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import analyzer.ClassPair;
import analyzer.IAnalyzerConfiguration;
import analyzer.IClassModel;
import analyzer.IRelationInfo;
import analyzer.ISystemModel;

public class AnalyzerRelationParserTest {

    @Test
    public void test() {
        // mock creation
        String _a_name = "ClassA", _b_name = "ClassB";

        IClassModel _a = mock(IClassModel.class, _a_name);
        when(_a.getName()).thenReturn(_a_name);
        IClassModel _b = mock(IClassModel.class, _b_name);
        when(_b.getName()).thenReturn(_b_name);

        // create systeModel
        Collection<? extends IClassModel> _classList = Arrays.asList(_a, _b);
        ISystemModel _sysModel = mock(ISystemModel.class);
        doReturn(_classList).when(_sysModel).getClasses();

        // start
        ISystemModel sysModel = runRelationAnalyzer(_sysModel);

        // get decorated classModels
        Collection<? extends IClassModel> classList = sysModel.getClasses();
        assertEquals(2, classList.size());
        IClassModel a = getClassFromIterableByName(_a_name, classList);
        IClassModel b = getClassFromIterableByName(_b_name, classList);

        // get relations
        Map<ClassPair, List<IRelationInfo>> relationList = sysModel.getRelations();
        assertEquals(0, relationList.size());

        // verify
//        verify(_sysModel).getClasses();
    }

    private ISystemModel runRelationAnalyzer(ISystemModel _sysModel) {
        IAnalyzerConfiguration config = mock(IAnalyzerConfiguration.class);
        AnalyzerRelationParser analyzerRelation = new AnalyzerRelationParser();
        ISystemModel sysModel = analyzerRelation.analyze(_sysModel, config);
        return sysModel;
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
