package model;

import analyzer.utility.ClassPair;
import analyzer.utility.IClassModel;
import analyzer.utility.IRelationInfo;
import config.ModelConfiguration;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SystemModelTest {
    @Test
    public void testLabelAndPrototype() {
        ModelConfiguration config = mock(ModelConfiguration.class);
        when(config.isRecursive()).thenReturn(false);
        when(config.getBlackList()).thenReturn(null);
        when(config.isVerbose()).thenReturn(false);
        when(config.filterSynthetic()).thenReturn(false);
        when(config.getClasses()).thenReturn(Collections.singletonList("javax.swing.JComponent"));

        SystemModel sys = SystemModel.getInstance(config);

        // rels
        Map<ClassPair, List<IRelationInfo>> rels = sys.getRelations();
        assertTrue(rels.entrySet().isEmpty());

        // classes
        Collection<ClassModel> classes = sys.getClasses();
        assertEquals(1, classes.size());
    }

    @Test
    public void recursiveParsing() {
        ModelConfiguration config = mock(ModelConfiguration.class);
        when(config.isRecursive()).thenReturn(true);
        when(config.getBlackList()).thenReturn(null);
        when(config.isVerbose()).thenReturn(false);
        when(config.filterSynthetic()).thenReturn(false);
        when(config.getClasses()).thenReturn(Collections.singletonList("javax.swing.JComponent"));

        SystemModel sys = SystemModel.getInstance(config);

        Set<String> actual = new HashSet<>();
        Set<String> expected = new HashSet<>(Arrays.asList("javax.swing.JComponent", "java.awt.Container",
                "java.awt.Component", "java.lang.Object", "java.awt.image.ImageObserver", "java.awt.MenuContainer",
                "java.io.Serializable", "javax.swing.TransferHandler$HasGetTransferHandler"));

        for (IClassModel x : sys.getClasses()) {
            actual.add(x.getName());
        }

        assertTrue("Not all interfaces get parsed", actual.containsAll(expected));
    }

}
