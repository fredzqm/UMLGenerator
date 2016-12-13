package main.java.runner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A GraphViz Process Runner.
 * <p>
 * Created by lamd on 12/11/2016.
 */
public class GraphVizRunner implements IRunner {
	private final String OUTPUT_FILE_EXTENSION = ".dot";

	private void write(IRunnerConfiguration config, String dotString) throws IOException {
		String outputDirectory = config.getOutputDirectory();
		String outputFile = outputDirectory + "/" + config.getFileName() + OUTPUT_FILE_EXTENSION;
		File file = new File(config.getOutputDirectory());
		file.mkdirs();

		try {
			FileWriter writer = new FileWriter(outputFile);
			writer.write(dotString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("[ ERROR ]: Unable to create file.");
		}
	}

	public void execute(IRunnerConfiguration config, String dotString) throws IOException, InterruptedException {
		write(config, dotString);

		StringBuilder command = new StringBuilder();
		String outputDirectory = config.getOutputDirectory();
		String outputFormat = config.getOutputFormat();
		String outputName = config.getFileName();

		// Create command "<executablePath> -T<outputFormat> <outputDirectory>/<outputName>.dot -o <outputDirectory>/<ouputName>.<outputFormat>
		command.append(config.getExecutablePath());
		command.append(" -T");
		command.append(outputFormat);
		command.append(" ").append(outputDirectory).append("/").append(outputName).append(OUTPUT_FILE_EXTENSION)
				.append(" ");
		command.append(" -o ");
		command.append(outputDirectory).append("/").append(outputName).append(".").append(outputFormat);

		// Execute the command line process.
		Process process = Runtime.getRuntime().exec(command.toString());
		process.waitFor();
	}

}
