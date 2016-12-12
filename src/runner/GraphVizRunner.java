package runner;

import analyzer.Job;
import configs.IConfiguration;
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

    private IGenerator generator;

    /**
     * Constructs a GraphVizRunner.
     *
     * @param sm SystemModel
     * @param config Configuration
     * @param jobs Jobs of Detected Patterns.
     * @throws IOException if it is unable to
     */
    public GraphVizRunner(ISystemModel sm, IConfiguration config, Collection<Job> jobs) throws IOException {
        this.generator = new GraphVizGenerator();
        this.generator.generate(sm, config, jobs);
    }

    private void write(String outputDirectory, String outputName, String data) throws IOException {
        File file = new File(outputDirectory);
        file.mkdirs();

        try {
            FileWriter writer = new FileWriter(outputDirectory + "/" + outputName);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("[ ERROR ]: Unable to create file.");
        }
    }

    public void execute(String executablePath, String outputDirectory, String outputFormat, String outputName) throws IOException, InterruptedException {
        final String OUTPUT_FILE_EXTENSION = ".dot";
        write(outputDirectory, outputName + OUTPUT_FILE_EXTENSION, getOutputString());

        StringBuilder command = new StringBuilder();

        // Create command "<executablePath> -T<outputFormat> <outputDirectory>/<outputName>.dot -o <outputDirectory>/<ouputName>.<outputFormat>
        command.append(executablePath);
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
