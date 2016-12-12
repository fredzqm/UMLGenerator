package runner;

import analyzer.Job;
import configs.Configuration;
import generator.GraphVizGenerator;
import generator.IGenerator;
import generator.ISystemModel;

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

    public void execute(String executablePath, String outputFormat, String outputName) {
        StringBuilder command = new StringBuilder();
        command.append(executablePath);
        command.append(" -T");
        command.append(outputFormat);
        command.append(" " + outputName);
        command.append("." + outputFormat);

//        p = Runtime.getRuntime().exec(command.toString());
//        p.waitFor();
//
//        BufferedReader reader =
//                new BufferedReader(new InputStreamReader(p.getInputStream()));
//
//        String line = "";
//        while ((line = reader.readLine())!= null) {
//            sb.append(line + "\n");
//        }
    }
}
