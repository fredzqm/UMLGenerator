

import java.io.IOException;

import config.Configuration;
import generator.GraphVizGenerator;
import generator.IGenerator;
import model.ASMParser;
import model.ASMServiceProvider;
import model.SystemModel;
import runner.GraphVizRunner;
import runner.IRunner;



public class UMLEngine implements Runnable {
	private Configuration config;

	public UMLEngine(Configuration configuration) {
		config = configuration;
	}

	@Override
	public void run() {
		// get the system model
		ASMServiceProvider asmParser = new ASMParser();
		SystemModel systemModel = new SystemModel(config.getClasses(), asmParser);

		// analyze

		// generate
		IGenerator generator = new GraphVizGenerator();
		String graphVisStr = generator.generate(systemModel, config, null);

		IRunner runner = new GraphVizRunner();
		try {
			runner.execute(config, graphVisStr);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
