package models;

import java.util.Collection;

/**
 * Created by lamd on 12/7/2016.
 */
public interface IMethodModel {
    ClassModel getParentClass();
    String getName();
//    Modifier getModifier();
//    Collection<TypeModel> getArguments();
//    TypeModel getReturnType();
//    Collection<IMethodModel> getDependentMethods();
//    Collection<FieldModel> getDependentFields();
    boolean isFinal();
}
