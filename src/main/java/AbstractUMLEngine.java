import analyzer.IASystemModel;
import generator.ISystemModel;

public abstract class AbstractUMLEngine implements Runnable {

	@Override
	public void run() {
		// get the system model
		IASystemModel systemModel = createSystemModel();

		// analyze
		systemModel = analyze(systemModel);

		// generate
		String graphVisStr = generate(systemModel);

		// run graphviz to generate the image
		executeRunner(graphVisStr);
	}

	abstract IASystemModel createSystemModel();

	abstract IASystemModel analyze(IASystemModel systemModel);

	abstract String generate(ISystemModel systemModel);

	abstract void executeRunner(String graphVisStr);
}
