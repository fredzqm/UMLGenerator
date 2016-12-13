package main.java;

import java.io.IOException;

import main.java.config.Configuration;
import main.java.generator.GraphVizGenerator;
import main.java.generator.IGenerator;
import main.java.model.ASMParser;
import main.java.model.ASMServiceProvider;
import main.java.model.SystemModel;
import main.java.runner.GraphVizRunner;
import main.java.runner.IRunner;

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
