package generator;

/**
 * An Interface for a Configuration file.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IConfiguration {
	
	/**
	 * 
	 * @return
	 */
    Iterable<String> getClasses();

    /**
     * 
     * @return
     */
    IFormat getFormat();

    /**
     * Returns the Output File Name.
     *
     * @return Output File Name.
     */
    String getFileName();

    /**
     * Returns the Output Directory.
     *
     * @return Output Directory.
     */
    String getOutputDirectory();

    /**
     * Returns the Output File's Format.
     * <p>
     * Example: ".png" --> "png"
     *
     * @return Output File's Format
     */
    String getOutputFormat();

}
