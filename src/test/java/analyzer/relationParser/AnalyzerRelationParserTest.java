package analyzer.relationParser;

import analyzer.utility.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AnalyzerRelationParserTest {

    @Test
    public void test1() {
        // mock creation
        String _a_name = "ClassA", _b_name = "ClassB";

        IClassModel _a = mock(IClassModel.class, _a_name);
        when(_a.getName()).thenReturn(_a_name);
        when(_a.getUnderlyingClassModel()).thenReturn(_a);
        IClassModel _b = mock(IClassModel.class, _b_name);
        when(_b.getName()).thenReturn(_b_name);
        when(_b.getUnderlyingClassModel()).thenReturn(_b);

        when(_a.getSuperClass()).thenReturn(_b);

        // create systemModel
        ISystemModel _sysModel = mock(ISystemModel.class);
        doReturn(Arrays.asList(_a, _b)).when(_sysModel).getClasses();

        // start
        ISystemModel sysModel = runRelationAnalyzer(_sysModel);

        // get decorated classModels
        Collection<? extends IClassModel> classList = sysModel.getClasses();
        assertEquals(2, classList.size());
        IClassModel a = getClassFromIterableByName(_a_name, classList);
        IClassModel b = getClassFromIterableByName(_b_name, classList);

        // get relations
        Map<ClassPair, List<IRelationInfo>> relations = sysModel.getRelations();
        List<IRelationInfo> fromAtoB = relations.get(new ClassPair(a, b));

        assertEquals(1, fromAtoB.size());
        assertTrue(fromAtoB.contains(new RelationExtendsClass()));

        // verify
        verify(_a).getSuperClass();
    }

    private ISystemModel runRelationAnalyzer(ISystemModel _sysModel) {
        AnalyzerRelationParser analyzerRelation = new AnalyzerRelationParser();
        ISystemModel sysModel = analyzerRelation.analyze(_sysModel, null);
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
