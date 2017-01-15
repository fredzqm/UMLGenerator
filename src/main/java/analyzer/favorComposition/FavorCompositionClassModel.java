package analyzer.favorComposition;

import analyzer.utility.IClassModel;
import analyzer.utility.IFieldModel;
import analyzer.utility.IMethodModel;
import utility.ClassType;

import java.util.Collection;

/**
 * Created by lamd on 1/15/2017.
 */
public class FavorCompositionClassModel implements IClassModel {
    private IClassModel decoratedClassModel;

    public FavorCompositionClassModel(IClassModel classModel) {
        this.decoratedClassModel = classModel;
    }

    @Override
    public String getName() {
        return this.decoratedClassModel.getName();
    }

    @Override
    public ClassType getType() {
        return this.decoratedClassModel.getType();
    }

    @Override
    public boolean isFinal() {
        return this.decoratedClassModel.isFinal();
    }

    @Override
    public boolean isStatic() {
        return this.decoratedClassModel.isStatic();
    }

    @Override
    public IClassModel getSuperClass() {
        return this.decoratedClassModel.getSuperClass();
    }

    @Override
    public Collection<? extends IClassModel> getInterfaces() {
        return this.decoratedClassModel.getInterfaces();
    }

    @Override
    public Collection<? extends IFieldModel> getFields() {
        return this.decoratedClassModel.getFields();
    }

    @Override
    public IClassModel getUnderlyingClassModel() {
        return this.decoratedClassModel.getUnderlyingClassModel();
    }

    @Override
    public Collection<? extends IMethodModel> getMethods() {
        return this.decoratedClassModel.getMethods();
    }

    @Override
    public String getNodeStyle() {
        return "color=\"orange\" ";
    }

    @Override
    public String getLabel() {
        return this.decoratedClassModel.getLabel();
    }
}
