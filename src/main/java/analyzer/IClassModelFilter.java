package analyzer;

import java.util.List;

/**
 * a filter for IClassModel
 *
 * @author zhang
 */
public abstract class IClassModelFilter implements IClassModel {
    private IClassModel classModel;
    
    /**
     * Constructs a Class Model Filter
     *
     * @param classModel
     *            classModel decorated.
     */
    public IClassModelFilter(IClassModel classModel) {
        this.classModel = classModel;
    }
    
    protected IClassModel getClassModel() {
        return classModel;
    }
    
    @Override
    public String getName() {
        return classModel.getName();
    }
    
    @Override
    public String getLabel() {
        return classModel.getLabel();
    }
    
    @Override
    public Iterable<? extends IFieldModel> getFields() {
        return classModel.getFields();
    }
    
    @Override
    public Iterable<? extends IMethodModel> getMethods() {
        return classModel.getMethods();
    }
    
    @Override
    public List<String> getStereoTypes() {
        return classModel.getStereoTypes();
    }
    
    @Override
    public IClassModel getSuperClass() {
        return classModel.getSuperClass();
    }
    
    @Override
    public Iterable<? extends IClassModel> getInterfaces() {
        return classModel.getInterfaces();
    }
    
}
