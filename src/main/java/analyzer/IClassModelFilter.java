package analyzer;

import java.util.List;

import utility.ClassType;

/**
 * a filter for IClassModel
 *
 * @author zhang
 */
public class IClassModelFilter implements IClassModel {
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

    public String getName() {
        return classModel.getName();
    }

    public ClassType getType() {
        return classModel.getType();
    }

    public boolean isFinal() {
        return classModel.isFinal();
    }

    public boolean isStatic() {
        return classModel.isStatic();
    }

    public IClassModel getSuperClass() {
        return classModel.getSuperClass();
    }

    public Iterable<? extends IClassModel> getInterfaces() {
        return classModel.getInterfaces();
    }

    public Iterable<? extends IFieldModel> getFields() {
        return classModel.getFields();
    }

    public Iterable<? extends IMethodModel> getMethods() {
        return classModel.getMethods();
    }

    public List<String> getStereoTypes() {
        return classModel.getStereoTypes();
    }

    public String getLabel() {
        return classModel.getLabel();
    }

}
