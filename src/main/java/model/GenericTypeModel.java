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
	private final ClazzTypeModel lowerBound;
	private final ClazzTypeModel upperBound;
	private final String key;

	GenericTypeModel(ClazzTypeModel lowerBound, ClazzTypeModel upperBound, String name) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.key = name;
	}

	public ClazzTypeModel getLowerBound() {
		return lowerBound;
	}

	public ClazzTypeModel getUpperBound() {
		return upperBound;
	}

	@Override
	public ClassModel getClassModel() {
		return lowerBound.getClassModel();
	}

	@Override
	public String getName() {
		return key;
	}

	private static GenericTypeModel getWildType(String name) {
		return new GenericTypeModel(ClassTypeModel.getObject(), null, name);
	}

	private static GenericTypeModel getLowerBounded(ClassTypeModel classTypeModel, String name) {
		return new GenericTypeModel(classTypeModel, null, name);
	}

	private static GenericTypeModel getUpperBounded(ClassTypeModel upperBound, String name) {
		return new GenericTypeModel(ClassTypeModel.getObject(), upperBound, name);
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
		return getLowerBounded(TypeParser.getType(bound), key);
	}

}
