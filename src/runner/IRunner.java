package runner;

import java.io.IOException;

/**
 * An Interface for a Runner.
 * <p>
 * Created by lamd on 12/11/2016.
 */
public interface IRunner {
    /**
     * Executes the Program that will generate files.
     *
     * @param executablePath  Path the Executable.
     * @param outputDirectory Path to Output Directory.
     * @param outputFormat    Desired Output Format.
     * @param outputName      Desired Output File Name
     * @throws IOException          if it is unable to write.
     * @throws InterruptedException if the write process is interrupted.
     */
    void execute(String executablePath, String outputDirectory, String outputFormat, String outputName) throws IOException, InterruptedException;

    /**
     * Returns the String data written.
     *
     * @return String written to file.
     */
    String getOutputString();
}
