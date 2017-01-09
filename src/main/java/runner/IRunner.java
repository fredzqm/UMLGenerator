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
     * @throws IOException
     *             if it is unable to write.
     * @throws InterruptedException
     *             if the write process is interrupted.
     */
    void execute(String dotString) throws IOException, InterruptedException;
}
