package model.type;

import model.ASMParser;
import model.ClassModel;

/**
 * serve as a place holder for generic type, we can should replace it with a
 * concrete class model
 * 
 * @author zhang
 *
 */
public class GenericTypeModel implements ClazzTypeModel {
	private final ClassModel lowerBound;
	private final ClassModel upperBound;
	private final String key;

	GenericTypeModel(ClassModel lowerBound, ClassModel upperBound, String name) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.key = name;
	}

	public ClassModel getLowerBound() {
		return lowerBound;
	}

	public ClassModel getUpperBound() {
		return upperBound;
	}

	@Override
	public ClassModel getClassModel() {
		return lowerBound;
	}

	@Override
	public String getName() {
		return key;
	}
	
	public static GenericTypeModel getWildType(String name) {
		return new GenericTypeModel(ASMParser.getClassByName("java.lang.Object"), null, name);
	}

	public static GenericTypeModel getLowerBounded(ClassModel lowerBounded, String name) {
		return new GenericTypeModel(lowerBounded, null, name);
	}

	public static GenericTypeModel getUpperBounded(ClassModel upperBound, String name) {
		return new GenericTypeModel(ASMParser.getClassByName("java.lang.Object"), upperBound, name);
	}

}
