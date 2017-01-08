package model;

import dummy.RelDummyClass;
import dummy.RelOtherDummyClass;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ClassModelRelationshipTest {
    String dummyClassName = RelDummyClass.class.getPackage().getName() + "." + RelDummyClass.class.getSimpleName();
    String otherDummyClassName = RelOtherDummyClass.class.getPackage().getName() + "."
            + RelOtherDummyClass.class.getSimpleName();

    @Test
    public void testHasARelationship() {

        ClassModel dummyClass = ASMParser.getClassByName(dummyClassName);
        ClassModel otherDummyClass = ASMParser.getClassByName(otherDummyClassName);

        Map<ClassModel, Integer> x = dummyClass.getHasRelation();
        assertEquals(1, x.size());
        assertEquals(2, (int) x.get(otherDummyClass));
    }

    @Test
    public void testDependsRelationship() {
        ClassModel dummyClass = ASMParser.getClassByName(dummyClassName);

        Collection<ClassModel> x = dummyClass.getDependsOn();
        assertEquals(0, x.size());
    }
}
