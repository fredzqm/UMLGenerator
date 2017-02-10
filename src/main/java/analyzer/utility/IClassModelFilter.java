package analyzer.utility;

import utility.ClassType;

import java.util.Collection;

/**
 * a filter for IClassModel
 *
 * @author zhang
 */
public abstract class IClassModelFilter implements IClassModel {
    private final IClassModel classModel;
    private final IClassModel underlyingModel;

    /**
     * Constructs a Class Model Filter
     *
     * @param classModel
     *            classModel decorated.
     */
    public IClassModelFilter(IClassModel classModel) {
        this.classModel = classModel;
        this.underlyingModel = classModel.getUnderlyingClassModel();
    }

    // TODO: Fred.
    public String getName() {
        return this.classModel.getName();
    }

    public ClassType getType() {
        return this.classModel.getType();
    }

    public boolean isFinal() {
        return this.classModel.isFinal();
    }

    public boolean isStatic() {
        return this.classModel.isStatic();
    }

    public boolean isSynthetic() {
        return this.classModel.isSynthetic();
    }

    public IClassModel getSuperClass() {
        return this.classModel.getSuperClass();
    }

    public Collection<? extends IClassModel> getInterfaces() {
        return this.classModel.getInterfaces();
    }

    public Collection<? extends IFieldModel> getFields() {
        return this.classModel.getFields();
    }

    public Collection<? extends IMethodModel> getMethods() {
        return this.classModel.getMethods();
    }

    @Override
    public boolean isSubClazzOf(IClassModel superClazz) {
        return this.classModel.isSubClazzOf(superClazz);
    }

    @Override
    public IClassModel getUnderlyingClassModel() {
        return underlyingModel;
    }

    @Override
    public String toString() {
        return this.classModel.toString();
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
