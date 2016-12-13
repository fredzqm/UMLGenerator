package main.java.config;

import main.java.generator.IGeneratorConfiguration;
import main.java.runner.IRunnerConfiguration;

/**
 * Created by lamd on 12/7/2016.
 */
public class Configuration implements IRunnerConfiguration, IGeneratorConfiguration {

	private Iterable<String> classes;
	private String buildPath;
	private String executionPath;
	private String outputExtension;
	private String outputName;

	public static Configuration getInstance() {
		Configuration conf = new Configuration();
		conf.setOutputExtension(".png");
		conf.setOutputName("out");

		return conf;
	}

	public Iterable<String> getClasses() {
		if (this.executionPath == null)
			throw new RuntimeException("Configuration.class: class list is null");
		return this.classes;
	}

	public void setClasses(Iterable<String> classes) {
		this.classes = classes;
	}

	public String getBuildPath() {
		return this.buildPath;
	}

	public void setBuildPath(String buildPath) {
		this.buildPath = buildPath;
	}

	public String getExecutionPath() {
		if (this.executionPath == null)
			throw new RuntimeException("Configuration.class: execution path is null");
		return this.executionPath;
	}

	public void setExecutionPath(String executionPath) {
		this.executionPath = executionPath;
	}

	public String getOutputExtension() {
		if (this.executionPath == null)
			throw new RuntimeException("Configuration.class: output extension is null");
		return this.outputExtension;
	}

	public void setOutputExtension(String outputExtension) {
		this.outputExtension = outputExtension;
	}

	public String getOutputName() {
		if (this.executionPath == null)
			throw new RuntimeException("Configuration.class: output file name is null");
		return this.outputName;
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOutputDirectory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOutputFormat() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getNodeSep() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getExecutablePath() {
		// TODO Auto-generated method stub
		return null;
	}

}
