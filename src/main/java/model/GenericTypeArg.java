package model;

import java.util.*;

import analyzer.ITypeModel;

/**
 * serve as a place holder for generic type, we can should replace it with a
 * concrete class model
 *
 * @author zhang
 */
abstract class GenericTypeArg implements TypeModel {
    private static GenericTypeArg wildType = new WildType();

    static GenericTypeArg getWildType() {
        return wildType;
    }

    static GenericTypeArg getLowerBounded(TypeModel classTypeModel) {
        return new LowerBound(classTypeModel);
    }

    static GenericTypeArg getUpperBounded(TypeModel upperBound) {
        return new UpperBound(upperBound);
    }

    @Override
    public Iterable<TypeModel> getSuperTypes() {
        return Collections.emptyList();
    }

    @Override
    public ClassModel getClassModel() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    public static class LowerBound extends GenericTypeArg {
        private TypeModel lowerBound;

        LowerBound(TypeModel lowerBound) {
            this.lowerBound = lowerBound;
        }

        public TypeModel getLowerBound() {
            return lowerBound;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof LowerBound) {
                LowerBound o = (LowerBound) obj;
                return lowerBound.equals(o.lowerBound);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return lowerBound.hashCode() * 31;
        }

        @Override
        public ClassModel getClassModel() {
            return lowerBound.getClassModel();
        }

        @Override
        public Iterable<TypeModel> getSuperTypes() {
            return Collections.singletonList(lowerBound);
        }

        @Override
        public TypeModel replaceTypeVar(Map<String, ? extends TypeModel> paramMap) {
            return new LowerBound(lowerBound.replaceTypeVar(paramMap));
        }

        @Override
        public Collection<ClassModel> getDependentClass() {
            return lowerBound.getDependentClass();
        }

        @Override
        public String toString() {
            return String.format("<? extends %s>", lowerBound.toString());
        }

    }

    public static class UpperBound extends GenericTypeArg {
        private TypeModel upperBound;

        UpperBound(TypeModel upperBound) {
            this.upperBound = upperBound;
        }

        public TypeModel getUpperBound() {
            return upperBound;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof UpperBound) {
                UpperBound o = (UpperBound) obj;
                return Objects.equals(upperBound, o.upperBound);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return upperBound.hashCode() * 3;
        }

        @Override
        public TypeModel replaceTypeVar(Map<String, ? extends TypeModel> paramMap) {
            return new UpperBound(upperBound.replaceTypeVar(paramMap));
        }

        @Override
        public Collection<ClassModel> getDependentClass() {
            return upperBound.getDependentClass();
        }

        @Override
        public String toString() {
            return String.format("<? super %s>", upperBound.toString());
        }

    }

    static class WildType extends GenericTypeArg {

        @Override
        public String toString() {
            return "*";
        }

        public boolean isWildCharacter() {
            return true;
        }

        @Override
        public Collection<ClassModel> getDependentClass() {
            return Collections.emptyList();
        }
    }
}
