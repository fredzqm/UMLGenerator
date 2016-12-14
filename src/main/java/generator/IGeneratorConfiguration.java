package generator;

import java.util.Collection;

/**
 * An Interface for Generator Configuration.
 * <p>
 * Created by lamd on 12/12/2016.
 * Edited by fineral on 12/13/2016.
 */
public interface IGeneratorConfiguration {

    /**
     * Returns the Node Seperation.
     *
     * @return Node Separation value.
     */
    double getNodeSep();
    
    /**
     * 
     * @return returns a list of all modifiers to ignore
     */
    public Collection<IModifier> getFilters();
}
