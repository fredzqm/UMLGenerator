package analyzer.utility;

import java.util.List;
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
     * Returns an Iterable of Relations contained within the SystemModel.
     *
     * @return Iterable of Relations contained within the SystemModel.
     */
    Map<ClassPair, List<IRelationInfo>> getRelations();

}
