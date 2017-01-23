package runner;

import config.IConfiguration;
import config.RunnerConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A GraphViz Process Runner.
 * <p>
 * Created by lamd on 12/11/2016.
 */
public class GraphVizRunner implements IRunner {
    private static final String OUTPUT_FILE_EXTENSION = "dot";
    private IRunnerConfiguration config;

    private GraphVizRunner(IRunnerConfiguration config) {
        this.config = config;
    }

    public static IRunner getInstance(IConfiguration iConfig) {
        return new GraphVizRunner(iConfig.createConfiguration(RunnerConfiguration.class));
    }

    public void execute(String dotString) throws IOException, InterruptedException {
        String outputFilePath = config.getOutputDirectory() + "/" + config.getFileName();
        String outputFilePathDot = outputFilePath + "." + OUTPUT_FILE_EXTENSION;
        String outputFilePathImage = outputFilePath + "." + config.getOutputFormat();

        // write dot string into the file system
        writeDOTFile(dotString, outputFilePathDot);

        // execute command to create the image file
        // Create command "<execPath> -T<format> <input> -o <output>
        String command = String.format("%s -T%s %s -o %s", config.getExecutablePath(), config.getOutputFormat(),
                outputFilePathDot, outputFilePathImage);
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }

    private void writeDOTFile(String dotString, String outputFilePathDot) throws IOException {
        File outputDir = new File(config.getOutputDirectory());
        outputDir.mkdirs();
        try {
            FileWriter writer = new FileWriter(outputFilePathDot);
            writer.write(dotString);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("[ ERROR ]: Unable to create file.");
        }
    }
}
