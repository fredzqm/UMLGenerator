package model;

import analyzer.ClassPair;
import analyzer.IClassModel;
import analyzer.IRelationInfo;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SystemModelTest {
    @Test
    public void testLabelAndPrototype() {
        IModelConfiguration config = new IModelConfiguration() {
            @Override
            public boolean isRecursive() {
                return false;
            }

            @Override
            public Iterable<String> getClasses() {
                return Collections.singletonList("javax.swing.JComponent");
            }
        };
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
        IModelConfiguration config = new IModelConfiguration() {
            @Override
            public boolean isRecursive() {
                return true;
            }

            @Override
            public Iterable<String> getClasses() {
                return Collections.singletonList("javax.swing.JComponent");
            }
        };
        SystemModel sys = SystemModel.getInstance(config);

        Set<String> actual = new HashSet<>();
        Set<String> expected = new HashSet<>(Arrays.asList("javax.swing.JComponent", "java.awt.Container",
                "java.awt.Component", "java.lang.Object", "java.awt.image.ImageObserver", "java.awt.MenuContainer",
                "java.io.Serializable", "javax.swing.TransferHandler$HasGetTransferHandler"));

        for (IClassModel x : sys.getClasses())
            actual.add(x.getName());

        assertTrue("Not all interfaces get parsed", actual.containsAll(expected));
    }

}
