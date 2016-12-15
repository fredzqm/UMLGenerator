package generator;

import java.util.Collection;

/**
 * An Interface for Generator Configuration.
 * <p>
 * Created by lamd on 12/12/2016. Edited by fineral on 12/13/2016.
 */
public interface IGeneratorConfiguration {

    /**
     * Returns the Node Seperation.
     *
     * @return Node Separation value.
     */
    double getNodeSep();

    /**
     * Return the set of Method Access Filters.
     *
     * @return Set of Method Access Filters.
     */
    Collection<IModifier> getFilters();

    /**
     * Returns the String of the Rank Direction.
     *
     * @return Rank Direction.
     */
    String getRankDir();
}
