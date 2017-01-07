package analyzer;

import java.util.Collection;

import analyzerRelationParser.Relation;
import generator.ISystemModel;

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
	Collection<? extends IClassModel> getClasses();

	/**
	 * Returns an Iterable of Relations contained within the SystemModel.
	 *
	 * @return Iterable of Relations contained within the SystemModel.
	 */
	default Iterable<Relation> getRelations() {
		throw new RuntimeException();
	}

}
