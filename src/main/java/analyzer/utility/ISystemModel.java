package analyzer.utility;

import java.util.Map;
import java.util.Set;

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

    /**
     * set the class set of systemModel
     *
     * @param classSet
     */
    void setClasses(Set<IClassModel> classSet);

    /**
     * Add node style to certain class,
     *
     * @param clazz
     * @param key
     * @param value
     */
    void addClassModelStyle(IClassModel clazz, String key, String value);

    /**
     * Add a stereotypes to class model
     *
     * @param clazz
     * @param stereotype
     */
    void addClassModelSteretypes(IClassModel clazz, String stereotype);

    /**
     * Add style to a relation
     *
     * @param from
     * @param to
     * @param relKey
     * @param key
     * @param value
     */
    void addStyleToRelation(IClassModel from, IClassModel to, String relKey, String key, String value);

    /**
     * Add a certain relation with specific styleMap
     *
     * @param from
     * @param to
     * @param relKey
     * @param styleMap
     */
    void addRelation(IClassModel from, IClassModel to, String relKey, StyleMap styleMap);

    /**
     * @return the relation maps within this SytemModel
     */
    Map<ClassPair, Map<String, StyleMap>> getRelations();

    /**
     * @param clazz
     * @return the node style of a specific class
     */
    String getNodeStyle(IClassModel clazz);

    /**
     * @param c
     * @return the stereotypes list of a specific class
     */
    Set<String> getStereoTypes(IClassModel c);

}
