package viewer;

import runner.RunnerConfiguration;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Views the outputted file.
 * <p>
 * Created by lamd on 1/11/2017.
 */
public class Viewer implements Runnable {
    private RunnerConfiguration config;

    /**
     * Constructs a Viewer.
     *
     * @param config IRunnerConfiguration config.
     */
    public Viewer(RunnerConfiguration config) {
        this.config = config;
    }

    /**
     * Run the default OS specific browser.
     */
    public void run() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        currentPath = currentPath.resolve(this.config.getOutputDirectory());
        currentPath = currentPath.resolve(String.format("%s.%s", this.config.getFileName(), this.config.getOutputFormat()));
        currentPath = currentPath.normalize();

        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(currentPath.toFile().toURI());
            } catch (IOException e) {
                throw new RuntimeException("Failed to execute Viewer", e);
            }
        } else {
            System.err.println("java.awt.Desktop is not supported on OS");
        }
    }
}
