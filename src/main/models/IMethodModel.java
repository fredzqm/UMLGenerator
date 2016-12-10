package models;

import java.util.Collection;

/**
 * Created by lamd on 12/7/2016.
 */
public interface IMethodModel {
    ClassModel getParentClass();
    String getName();
    Modifier getModifier();
    Collection<ITypeModel> getArguments();
    ITypeModel getReturnType();
    Collection<IMethodModel> getDependentMethods();
    Collection<IFieldModel> getDependentFields();
    boolean isFinal();
}
