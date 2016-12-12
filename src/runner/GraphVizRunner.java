package runner;

import analyzer.Job;
import configs.IConfiguration;
import generator.GraphVizGenerator;
import generator.IGenerator;
import generator.ISystemModel;

import java.io.IOException;
import java.util.Collection;

/**
 * A GraphViz Process Runner.
 * <p>
 * Created by lamd on 12/11/2016.
 */
public class GraphVizRunner implements IRunner {
    private IGenerator generator;

    public GraphVizRunner(ISystemModel sm, IConfiguration config, Collection<Job> jobs) {
        this.generator = new GraphVizGenerator();
        this.generator.generate(sm, config, jobs);
    }

    public void execute(String executablePath, String outputDirectory, String outputFormat, String outputName) throws IOException, InterruptedException {
        StringBuilder command = new StringBuilder();

        // Create command "<executablePath> -T<outputFormat> <outputDirectory>/<outputName>.dot -o <outputDirectory>/<ouputName>.<outputFormat>
        command.append(executablePath);
        command.append(" -T");
        command.append(outputFormat);
        command.append(" " + outputDirectory + "/" + outputName + ".dot ");
        command.append(" -o ");
        command.append(outputDirectory + "/" + outputName + "." + outputFormat);

        // Execute the command line process.
        Process process = Runtime.getRuntime().exec(command.toString());
        process.waitFor();
    }

    public String getOutputString() {
        return this.generator.getOutputString();
    }
}
