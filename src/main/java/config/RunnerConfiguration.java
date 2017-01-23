package config;

import runner.IRunnerConfiguration;

/**
 * A RunnerConfiguration.
 * <p>
 * Created by lamd on 1/11/2017.
 */
public class RunnerConfiguration implements IRunnerConfiguration, Configurable {
    public static final String OUTPUT_FORMAT = "outputFormat";
    public static final String OUTPUT_DIRECTORY = "outputDir";
    public static final String EXECUTABLE_PATH = "executablePath";
    public static final String FILE_NAME = "fileName";

    private IConfiguration config;

    /**
     * Empty Constructor for newInstance calls.
     */
    public RunnerConfiguration() {
        this.config = null;
    }

    @Override
    public void setup(IConfiguration config) {
        this.config = config;
        this.config.setIfMissing(OUTPUT_FORMAT, "svg");
        this.config.setIfMissing(OUTPUT_DIRECTORY, "output");
        this.config.setIfMissing(EXECUTABLE_PATH, "dot");
        this.config.setIfMissing(FILE_NAME, "output");
    }

    @Override
    public String getOutputFormat() {
        return this.config.getValue(OUTPUT_FORMAT);
    }

    @Override
    public String getOutputDirectory() {
        return this.config.getValue(OUTPUT_DIRECTORY);
    }

    @Override
    public String getExecutablePath() {
        return this.config.getValue(EXECUTABLE_PATH);
    }

    @Override
    public String getFileName() {
        return this.config.getValue(FILE_NAME);
    }
}
