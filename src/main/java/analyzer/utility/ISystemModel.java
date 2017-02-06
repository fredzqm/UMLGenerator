package analyzer.utility;

import java.util.List;
import java.util.Map;
import java.util.Set;

import analyzer.relationParser.IRelationInfo;

/**
 * An Interface for System Models.
 * <p>
 * Created by lamd on 12/9/2016.
 */
public interface ISystemModel {
    /**
     * Returns an Iterable of Class Models contained within the System Model.
     *
     * @return Iterable of Class Models.
     */
    Set<? extends IClassModel> getClasses();

    void addClassModelStyle(IClassModel clazz, String key, String value);

    void addStyleToRelation(IClassModel from, IClassModel to, String relKey, String key, String value);

    void setClasses(Set<IClassModel> classSet);

    void addClassModelSteretypes(IClassModel clazz, String stereotype);

    void addRelation(IClassModel from, IClassModel to, String relKey, StyleMap styleMap);

    Map<ClassPair, Map<String, StyleMap>> getRelations();

    String getNodeStyle(IClassModel c);

    List<String> getStereoTypes(IClassModel c);

}
