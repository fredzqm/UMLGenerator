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
    public String getConfigDir() {
        return "";
    }

    @Override
    public String getOutputFormat() {
        String value = this.config.getValue(OUTPUT_FORMAT);
        if (value == null) {
            throw new RuntimeException("output format is not configured, configure with key " + OUTPUT_FORMAT);
        }
        return value;
    }

    @Override
    public String getOutputDirectory() {
        String value = this.config.getValue(OUTPUT_DIRECTORY);
        if (value == null) {
            throw new RuntimeException("output directory is not configured, configure with key " + OUTPUT_DIRECTORY);
        }
        return value;
    }

    @Override
    public String getExecutablePath() {
        String value = this.config.getValue(EXECUTABLE_PATH);
        if (value == null) {
            throw new RuntimeException("executable path is not configured, configure with key " + EXECUTABLE_PATH);
        }
        return value;
    }

    @Override
    public String getFileName() {
        String value = this.config.getValue(FILE_NAME);
        if (value == null) {
            throw new RuntimeException("file name is not configured, configure with key " + FILE_NAME);
        }
        return value;
    }
}
