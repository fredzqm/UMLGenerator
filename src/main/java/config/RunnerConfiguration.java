package config;

import runner.IRunnerConfiguration;

/**
 * A RunnerConfiguration.
 * <p>
 * Created by lamd on 1/11/2017.
 */
public class RunnerConfiguration implements IRunnerConfiguration, Configurable {
    public static final String OUTPUT_FORMAT = "runner_output_format";
    public static final String OUTPUT_DIRECTORY = "runner_output_directory";
    public static final String EXECUTABLE_PATH = "runner_executable_path";
    public static final String FILE_NAME = "runner_file_name";

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
    }

    @Override
    public String getOutputFormat() {
        return this.config.getValue(RunnerConfiguration.OUTPUT_FORMAT);
    }

    @Override
    public String getOutputDirectory() {
        return this.config.getValue(RunnerConfiguration.OUTPUT_DIRECTORY);
    }

    @Override
    public String getExecutablePath() {
        return this.config.getValue(RunnerConfiguration.EXECUTABLE_PATH);
    }

    @Override
    public String getFileName() {
        return this.config.getValue(RunnerConfiguration.FILE_NAME);
    }
}
