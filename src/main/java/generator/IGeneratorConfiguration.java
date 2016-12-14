package generator;

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
     * @return true if you should ignore public entities
     */
    public boolean isNoPublic();

    /**
     * 
     * @return true if you should ignore private entities
     */
	public boolean isNoPrivate();
	
	/**
	 * 
	 * @return true if you should ignore protected entities
	 */
	public boolean isNoProtected();
}
