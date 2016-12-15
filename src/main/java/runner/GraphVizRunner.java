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
	private static final String OUTPUT_FILE_EXTENSION = ".dot";

	public void execute(IRunnerConfiguration config, String dotString) throws IOException, InterruptedException {
		// String outputDirectory = config.getOutputDirectory();
		String outputFilePath = config.getOutputDirectory() + "/" + config.getFileName();

		File outputDir = new File(config.getOutputDirectory());
		outputDir.mkdirs();
		try {
			FileWriter writer = new FileWriter(outputFilePath + OUTPUT_FILE_EXTENSION);
			writer.write(dotString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("[ ERROR ]: Unable to create file.");
		}

		// Create command "<execPath> -T<format> <input> -o <output>
		String command = String.format("%s -T%s %s -o %s.%s", config.getExecutablePath(), config.getOutputFormat(),
				outputFilePath + OUTPUT_FILE_EXTENSION, outputFilePath, config.getOutputFormat());
		// Execute the command line process.
		Process process = Runtime.getRuntime().exec(command.toString());
		process.waitFor();
	}

}
