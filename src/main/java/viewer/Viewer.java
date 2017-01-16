package viewer;

import runner.IRunnerConfiguration;

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
    private IRunnerConfiguration config;

    public Viewer(IRunnerConfiguration config) {
        this.config = config;
    }

    public void run() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        currentPath = currentPath.resolve(this.config.getOutputDirectory());
        currentPath = currentPath.resolve(String.format("%s.%s", this.config.getFileName(), this.config.getOutputFormat()));
        currentPath = currentPath.normalize();

        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(currentPath.toFile().toURI());
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to execute Viewer");
            }
        } else {
            System.err.println("java.awt.Desktop is not supported on OS");
        }
    }
}
