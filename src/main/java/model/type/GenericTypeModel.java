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

	private static GenericTypeModel getWildType(String name) {
		return new GenericTypeModel(ASMParser.getObject(), null, name);
	}

	private static GenericTypeModel getLowerBounded(ClassModel lowerBounded, String name) {
		return new GenericTypeModel(lowerBounded, null, name);
	}

	private static GenericTypeModel getUpperBounded(ClassModel upperBound, String name) {
		return new GenericTypeModel(ASMParser.getObject(), upperBound, name);
	}

	/**
	 * 
	 * @param arg
	 *            the argument description string found in class or method's
	 *            signature
	 * @return the generic type model representing this
	 */
	public static GenericTypeModel parse(String arg) {
		// E:Ljava/lang/Object
		String[] sp = arg.split(":");
		String key = sp[0];
		// has a lower bound
		ClassModel bound = ASMParser.getClassByName(sp[sp.length - 1].substring(1));
		return getLowerBounded(bound, key);
	}

}
