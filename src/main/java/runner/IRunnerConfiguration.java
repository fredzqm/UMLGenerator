package runner;

/**
 * An interface for a Runner Configuration.
 * <p>
 * Created by lamd on 12/12/2016.
 */
public interface IRunnerConfiguration {
    /**
     * Returns the Output File's Format.
     * <p>
     * Example: ".png" --> "png"
     *
     * @return Output File's Format
     */
    String getOutputFormat();
    
    /**
     * Returns the Output Directory.
     *
     * @return Output Directory.
     */
    String getOutputDirectory();
    
    /**
     * Returns the String of the executable path.
     *
     * @return Executable Path String.
     */
    String getExecutablePath();
    
    /**
     * @return the file name without the extension
     */
    String getFileName();
}
