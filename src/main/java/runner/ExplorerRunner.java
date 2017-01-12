package runner;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by lamd on 1/11/2017.
 */
public class ExplorerRunner implements IRunner {
    private IRunnerConfiguration config;

    public ExplorerRunner(IRunnerConfiguration config) {
        this.config = config;
    }

    @Override
    public void execute(String dotString) throws IOException, InterruptedException {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(currentPath.toString(), this.config.getOutputDirectory(), String.format("%s.%s", this.config.getFileName(), this.config.getOutputFormat()));
        String command = String.format("%s %s", this.config.getExecutablePath(), filePath);

        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
    }
}
