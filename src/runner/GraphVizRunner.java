package runner;

import analyzer.Job;
import configs.Configuration;
import generator.GraphVizGenerator;
import generator.IGenerator;
import generator.ISystemModel;

import java.io.BufferedReader;
import java.util.Collection;

/**
 * Created by lamd on 12/11/2016.
 */
public class GraphVizRunner {
    private IGenerator generator;

    public GraphVizRunner(ISystemModel sm, Configuration config, Collection<Job> jobs) {
        this.generator = new GraphVizGenerator();
        this.generator.generate(sm, config, jobs);
    }

    public void execute(Configuration config) {
        StringBuilder command = new StringBuilder();
        command.append(config.getExecutable());

        p = Runtime.getRuntime().exec(config.getExecutable() + " " + );
        p.waitFor();

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line = "";
        while ((line = reader.readLine())!= null) {
            sb.append(line + "\n");
        }
    }
}
