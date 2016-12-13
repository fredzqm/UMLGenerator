package runner;

import java.io.IOException;

/**
 * An Interface for a Runner.
 * <p>
 * Created by lamd on 12/11/2016.
 */
public interface IRunner {
	void execute(IConfiguration config) throws IOException, InterruptedException;
}
