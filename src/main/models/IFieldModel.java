package models;

/**
 * Created by lamd on 12/9/2016.
 */
public interface IFieldModel {
    Modifier getModifier();
    String getName();
    TypeModel getType();
    String getTypeName();
}
