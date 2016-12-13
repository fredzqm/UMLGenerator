package runner;

import configs.IConfiguration;
import generator.IGeneratorConfiguration;

/**
 * Created by lamd on 12/12/2016.
 */
public interface IRunnerConfiguration extends IConfiguration, IGeneratorConfiguration {
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
     * Returns the String of the executable path.
     *
     * @return Executable Path String.
     */
    String getExecutablePath();

    /**
     * Sets the Executable Path.
     *
     * @param executablePath new Executable Path to use.
     */
    void setExecutablePath(String executablePath);
}
