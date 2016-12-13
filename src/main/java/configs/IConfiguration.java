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
     * @return Iterable of the Classes.
     */
    Iterable<String> getClasses();

    /**
     * Returns the Format of the Configuration.
     *
     * @return Format of the Configuration.
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
}
