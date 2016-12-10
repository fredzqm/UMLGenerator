package models;

import java.util.List;

/**
 * Created by lamd on 12/9/2016.
 */
public interface IClassModel {
    String getClassName();
    ClassType getType();
    List<IFieldModel> getFields();
    List<IMethodModel> getMethods();
    String getSuperClass();
    List<IClassModel> getInterfaces();
    List<IClassModel> getHasRelation();
    List<IClassModel> getDependsRelation();


}
