package model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * serve as a place holder for generic type, we can should replace it with a
 * concrete class model
 *
 * @author zhang
 */
class GenericTypeArg implements TypeModel {
    private static GenericTypeArg wildType = getUpperBounded(null);
    private final TypeModel lowerBound;
    private final TypeModel upperBound;

    GenericTypeArg(TypeModel lowerBound, TypeModel upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public static GenericTypeArg getWildType() {
        return wildType;
    }

    static GenericTypeArg getLowerBounded(TypeModel classTypeModel) {
        return new GenericTypeArg(classTypeModel, null);
    }

    static GenericTypeArg getUpperBounded(TypeModel upperBound) {
        return new GenericTypeArg(ASMParser.getObject(), upperBound);
    }

    public TypeModel getLowerBound() {
        return lowerBound;
    }

    public TypeModel getUpperBound() {
        return upperBound;
    }

    @Override
    public ClassModel getClassModel() {
        return lowerBound.getClassModel();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GenericTypeArg) {
            GenericTypeArg o = (GenericTypeArg) obj;
            return Objects.equals(upperBound, o.upperBound) && Objects.equals(lowerBound, o.lowerBound);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (upperBound == null ? 0 : upperBound.hashCode() * 31) + lowerBound.hashCode();
    }

    @Override
    public Iterable<TypeModel> getSuperTypes() {
        return Arrays.asList(lowerBound);
    }

    @Override
    public TypeModel replaceTypeVar(Map<String, ? extends TypeModel> paramMap) {
        if (upperBound == null)
            return new GenericTypeArg(lowerBound.replaceTypeVar(paramMap), null);
        return new GenericTypeArg(lowerBound.replaceTypeVar(paramMap), upperBound.replaceTypeVar(paramMap));
    }

	@Override
	public Collection<ClassModel> getDependsOn() {
		return lowerBound.getDependsOn();
	}

}
