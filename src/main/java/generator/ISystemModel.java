package generator;


import generator.classParser.IClassModel;
import generator.relParser.Relation;

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
    Iterable<? extends IClassModel> getClasses();

    /**
     * @return the list of relationships that should be shown in the graph
     */
    Iterable<Relation> getRelations();
}
