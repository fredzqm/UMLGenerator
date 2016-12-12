package configs;


/**
 * An Interface for a Configuration file.
 * <p>
 * Created by lamd on 12/7/2016.
 */
public interface IConfiguration {
    /**
     * Returns an Iterable of the classes.
     *
     * @return
     */
    Iterable<String> getClasses();

    /**
     * Returns the Format of the Configuration.
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
     * Set the Output File Name.
     *
     * @param name New Output File Name.
     */
    void setFileName(String name);

    /**
     * Returns the Output Directory.
     *
     * @return Output Directory.
     */
    String getOutputDirectory();

    /**
     * Set the Output Directory.
     *
     * @param outputDirectory New Output Directory.
     */
    void setOutputDirectory(String outputDirectory);

    /**
     * Returns the Output File's Format.
     * <p>
     * Example: ".png" --> "png"
     *
     * @return Output File's Format
     */
    String getOutputFormat();

    /**
     * Set the Output Format.
     *
     * @param outputFormat New Output Format.
     */
    void setOutputFormat(String outputFormat);
}
