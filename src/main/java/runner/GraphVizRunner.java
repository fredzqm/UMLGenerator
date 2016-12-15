package runner;

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

    public void execute(IRunnerConfiguration config, String dotString) throws IOException, InterruptedException {
        String outputFilePath = config.getOutputDirectory() + "/" + config.getFileName();
        String outputFilePathDot = outputFilePath + "." + OUTPUT_FILE_EXTENSION;
        String outputFilePathImage = outputFilePath + "." + config.getOutputFormat();

        // write dot string into the file system
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

        // execute command to create the image file
        // Create command "<execPath> -T<format> <input> -o <output>
        String command = String.format("%s -T%s %s -o %s", config.getExecutablePath(), config.getOutputFormat(),
                outputFilePathDot, outputFilePathImage);
        Process process = Runtime.getRuntime().exec(command.toString());
        process.waitFor();
    }

}
