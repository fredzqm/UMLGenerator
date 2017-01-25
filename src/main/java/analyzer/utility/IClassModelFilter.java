package analyzer.utility;

import utility.ClassType;

import java.util.Collection;
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
     * @param classModel classModel decorated.
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

    public boolean isSynthetic() {
        return classModel.isSynthetic();
    }

    public IClassModel getSuperClass() {
        return classModel.getSuperClass();
    }

    public Collection<? extends IClassModel> getInterfaces() {
        return classModel.getInterfaces();
    }

    public Collection<? extends IFieldModel> getFields() {
        return classModel.getFields();
    }

    public Collection<? extends IMethodModel> getMethods() {
        return classModel.getMethods();
    }

    public List<String> getStereoTypes() {
        return classModel.getStereoTypes();
    }

    public String getLabel() {
        return classModel.getLabel();
    }

    public String getNodeStyle() {
        return classModel.getNodeStyle();
    }

    @Override
    public IClassModel getUnderlyingClassModel() {
        return classModel.getUnderlyingClassModel();
    }

    @Override
    public String toString() {
        return classModel.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IClassModel) {
            IClassModel c = (IClassModel) obj;
            return getUnderlyingClassModel().equals(c.getUnderlyingClassModel());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getUnderlyingClassModel().hashCode();
    }
}
