package analyzer;

import generator.ISystemModel;
import generator.classParser.IClassModel;
import generator.relationshipParser.Relation;

/**
 * An Interface for System Models.
 * <p>
 * Created by lamd on 12/9/2016.
 */
public interface IASystemModel extends ISystemModel{

	/**
	 * Returns an Iterable of Class Models contained within the System Model.
	 *
	 * @return Iterable of Class Models.
	 */
	Iterable<? extends IClassModel> getClasses();

	/**
	 * Returns an Iterable of Relations contained within the SystemModel.
	 *
	 * @return Iterable of Relations contained within the SystemModel.
	 */
	Iterable<Relation> getRelations();

}
