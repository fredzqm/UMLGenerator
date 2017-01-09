package model;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import dummy.RelDummyClass;
import dummy.RelOtherDummyClass;

public class ClassModelRelationshipTest {
	String relDummyClassName = RelDummyClass.class.getPackage().getName() + "." + RelDummyClass.class.getSimpleName();
	String otherDummyClassName = RelOtherDummyClass.class.getPackage().getName() + "."
			+ RelOtherDummyClass.class.getSimpleName();

	@Test
	public void testHasARelationship() {

		ClassModel dummyClass = ASMParser.getClassByName(relDummyClassName);
		ClassModel otherDummyClass = ASMParser.getClassByName(otherDummyClassName);

		Iterator<ClassModel> itr = dummyClass.getHasTypes().iterator();
		assertEquals(otherDummyClass, itr.next());
		assertFalse(itr.hasNext());
	}

	@Test
	public void testDependsRelationship() {
		ClassModel dummyClass = ASMParser.getClassByName(relDummyClassName);

		Collection<ClassModel> x = dummyClass.getClassDependsOn();
		assertEquals(0, x.size());
	}
}
