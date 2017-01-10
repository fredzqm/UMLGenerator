package model;

import java.util.Collection;
import java.util.Map;

import utility.IMapper;

/**
 * A decorate class for array
 *
 * @author zhang
 */
class ArrayTypeModel implements TypeModel {
    private final TypeModel arrayType;
    private final int dimension;
    
    /**
     * creates an array type
     * 
     * @param arrayType
     * @param dimension
     */
    ArrayTypeModel(TypeModel arrayType, int dimension) {
        if (arrayType.getClass() == ArrayTypeModel.class) {
            throw new RuntimeException("Array type model cannot be an array of another array");
        }
        this.dimension = dimension;
        this.arrayType = arrayType;
    }
    
    @Override
    public ClassModel getClassModel() {
        return arrayType.getClassModel();
    }
    
    @Override
    public String getName() {
        StringBuilder sb = new StringBuilder(arrayType.getName());
        for (int i = 0; i < dimension; i++) {
            sb.append("[]");
        }
        return sb.toString();
    }
    
    @Override
    public int getDimension() {
        return dimension;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArrayTypeModel) {
            ArrayTypeModel o = (ArrayTypeModel) obj;
            return dimension == o.dimension && arrayType.equals(o.arrayType);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return dimension * 31 + arrayType.hashCode();
    }
    
    @Override
    public Iterable<TypeModel> getSuperTypes() {
        IMapper<TypeModel, TypeModel> mapper = (x) -> new ArrayTypeModel(x, dimension);
        return mapper.map(arrayType.getSuperTypes());
    }
    
    @Override
    public TypeModel replaceTypeVar(Map<String, ? extends TypeModel> paramMap) {
        return new ArrayTypeModel(arrayType.replaceTypeVar(paramMap), dimension);
    }
    
    @Override
    public TypeModel assignTo(ClassModel clazz) {
        TypeModel t = arrayType.assignTo(clazz);
        if (t != null)
            return new ArrayTypeModel(t, dimension);
        return null;
    }
    
    @Override
    public Collection<ClassModel> getDirectDependsOnClass() {
        return arrayType.getDirectDependsOnClass();
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
}
