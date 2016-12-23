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

    
//	/**
//	 * Returns the IClassModel of the Model's superclass.
//	 *
//	 * @return Model's superclass (can be null if the class is Object)
//	 */
//	Iterable<Relation> getSuperClass();
//
//	/**
//	 * Returns the List of classes the Model inherits from.
//	 *
//	 * @return Intefaces the model inherits.
//	 */
//    Iterable<Relation> getInterfaces();
//
//	/**
//	 * Returns the Map of the Model's Has-A relation.
//	 *
//	 * @return Map from IClassModel to Integer with a Has-A relationship with
//	 *         the Model. A positive integer m represents the number of single
//	 *         composition. A negative integer -m represents containing at least
//	 *         m composition, but up to infinity
//	 */
//    Iterable<Relation> getHasRelation();
//
//	/**
//	 * Returns the List of the Model's Depends-On Relation.
//	 *
//	 * @return List of Classes with a Depends-On relationship with the Model.
//	 */
//    Iterable<Relation> getDependsRelation();


	Iterable<Relation> getRelations();}
