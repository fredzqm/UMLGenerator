package runner;

import java.io.IOException;

/**
 * An Interface for a Runner.
 * <p>
 * Created by lamd on 12/11/2016.
 */
public interface IRunner {
    void execute(String executablePath, String outputDirectory, String outputFormat, String outputName) throws IOException, InterruptedException;
}
