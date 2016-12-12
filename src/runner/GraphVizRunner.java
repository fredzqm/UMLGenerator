package runner;

import java.io.IOException;

/**
 * A GraphViz Process Runner.
 * <p>
 * Created by lamd on 12/11/2016.
 */
public class GraphVizRunner implements IRunner {

	public void execute(IConfiguration config) throws IOException, InterruptedException {
		StringBuilder command = new StringBuilder();
		command.append(config.getExecutionPath());
		command.append(" -T");
		command.append(config.getOutputFormat());
		command.append(" " + config.getOutputDirectory() + "/" + config.getOutputName() + ".dot ");
		command.append(" -o ");
		command.append(config.getOutputDirectory() + "/" + config.getOutputName() + "." + config.getOutputFormat());

		Process process = Runtime.getRuntime().exec(command.toString());
		process.waitFor();
	}

}
