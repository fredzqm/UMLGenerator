package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import config.Configuration;
import dummy.RelDummyClass;
import dummy.RelOtherDummyClass;

public class ClassModelRelationshipTest {
	String dummyClassName = RelDummyClass.class.getPackage().getName() + "." + RelDummyClass.class.getSimpleName();
	String otherDummyClassName = RelOtherDummyClass.class.getPackage().getName() + "."
			+ RelOtherDummyClass.class.getSimpleName();

	private ASMClassTracker getASMParser() {
		Configuration config = Configuration.getInstance();
		List<String> classList = new ArrayList<>(Arrays.asList(dummyClassName));
		config.setClasses(classList);
		config.setRecursive(true);
		ASMClassTracker asmParser = ASMParser.getInstance(config);
		asmParser.freezeClassCreation();
		
		ClassModel dummyClass = asmParser.getClassByName(dummyClassName);
		assertEquals(dummyClassName, dummyClass.getName());

		ClassModel otherDummyClass = asmParser.getClassByName(otherDummyClassName);
		assertEquals(otherDummyClassName, otherDummyClass.getName());
		
		return asmParser;
	}
	
	@Test
	public void testHasARelationship() {
		ASMClassTracker asmParser = getASMParser();

		ClassModel dummyClass = asmParser.getClassByName(dummyClassName);
		ClassModel otherDummyClass = asmParser.getClassByName(otherDummyClassName);

		Map<ClassModel, Integer> x = dummyClass.getHasRelation();
		assertEquals(1, x.size());
		assertEquals(2, (int) x.get(otherDummyClass));
	}

	@Test
	public void testDependsRelationship() {
		ASMClassTracker asmParser = getASMParser();

		ClassModel dummyClass = asmParser.getClassByName(dummyClassName);
		
		Collection<ClassModel> x = dummyClass.getDependsRelation();
		assertEquals(0, x.size());
	}
}
