package runner;

import analyzer.Job;
import generator.GraphVizGenerator;
import generator.IGenerator;
import generator.ISystemModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

/**
 * A GraphViz Process Runner.
 * <p>
 * Created by lamd on 12/11/2016.
 */
public class GraphVizRunner implements IRunner {
    private final String OUTPUT_FILE_EXTENSION = ".dot";


    private IGenerator generator;
    private IRunnerConfiguration config;

    /**
     * Constructs a GraphVizRunner.
     *
     * @param sm     SystemModel
     * @param config Configuration
     * @param jobs   Jobs of Detected Patterns.
     * @throws IOException if it is unable to
     */
    public GraphVizRunner(ISystemModel sm, IRunnerConfiguration config, Collection<Job> jobs) throws IOException {
        this.generator = new GraphVizGenerator();
        this.config = config;
        this.generator.generate(sm, config, jobs);
    }

    private void write() throws IOException {
        String outputDirectory = this.config.getOutputDirectory();
        String outputFile = outputDirectory + "/" + this.config.getFileName() + OUTPUT_FILE_EXTENSION;
        File file = new File(this.config.getOutputDirectory());
        file.mkdirs();

        try {
            FileWriter writer = new FileWriter(outputFile);
            writer.write(getOutputString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("[ ERROR ]: Unable to create file.");
        }
    }

    public void execute() throws IOException, InterruptedException {
        write();

        StringBuilder command = new StringBuilder();
        String outputDirectory = this.config.getOutputDirectory();
        String outputFormat = this.config.getOutputFormat();
        String outputName = config.getFileName();

        // Create command "<executablePath> -T<outputFormat> <outputDirectory>/<outputName>.dot -o <outputDirectory>/<ouputName>.<outputFormat>
        command.append(this.config.getExecutablePath());
        command.append(" -T");
        command.append(outputFormat);
        command.append(" ").append(outputDirectory).append("/").append(outputName).append(OUTPUT_FILE_EXTENSION).append(" ");
        command.append(" -o ");
        command.append(outputDirectory).append("/").append(outputName).append(".").append(outputFormat);

        // Execute the command line process.
        Process process = Runtime.getRuntime().exec(command.toString());
        process.waitFor();
    }

    public String getOutputString() {
        return this.generator.getOutputString();
    }
}
