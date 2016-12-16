package model;

import generator.IClassModel;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class SystemModelTest {

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

        Set<String> acutal = new HashSet<>();
        Set<String> expect = new HashSet<>(Arrays.asList("javax.swing.JComponent", "java.awt.Container",
                "java.awt.Component", "java.lang.Object", "java.awt.image.ImageObserver", "java.awt.MenuContainer",
                "java.io.Serializable", "javax.swing.TransferHandler$HasGetTransferHandler"));

        for (IClassModel x : sys.getClasses())
            acutal.add(x.getName());

        assertEquals(expect, acutal);
    }

}
