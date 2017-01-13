package model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import analyzer.IMethodModel;
import utility.MethodType;
import utility.Modifier;

/**
 * Representing method in java program
 *
 * @author zhang
 */
interface MethodModel extends IMethodModel {

    Map<String, GenericTypeParam> getParamsMap();

    ClassModel getBelongTo();

    String getName();

    MethodType getMethodType();

    Modifier getModifier();

    boolean isStatic();

    Signature getSignature();

    List<TypeModel> getArguments();

    TypeModel getReturnType();

    Collection<MethodModel> getCalledMethods();

    Collection<FieldModel> getAccessedFields();

}
