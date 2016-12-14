package config;

import java.util.ArrayList;
import generator.IGeneratorConfiguration;
import model.IModelConfiguration;
import runner.IRunnerConfiguration;

/**
 * Created by lamd on 12/7/2016.
 * Edited by fineral on 12/13/2016
 */
public class Configuration implements IRunnerConfiguration, IGeneratorConfiguration, IModelConfiguration {

    private Iterable<String> classes;
    private String executablePath;
    private String outputFormat;
    private String outputDirectory;
    private String fileName;
    private double nodesep;
    private boolean noPublic;
    private boolean noPrivate;
    private boolean noProtected;
    private boolean isRecursive;
    
    public static Configuration getInstance() {
        Configuration conf = new Configuration();
        conf.setOutputFormat(".png");
        conf.setFileName("out");
        conf.setNodesep(1);
        conf.setClasses(new ArrayList<String>());

        return conf;
    }
    
    public Iterable<String> getClasses() {
		return classes;
	}

	public void setClasses(Iterable<String> classes) {
		this.classes = classes;
	}

	public String getExecutablePath() {
		return executablePath;
	}

	public void setExecutablePath(String executablePath) {
		this.executablePath = executablePath;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String outputExtension) {
		this.outputFormat = outputExtension;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public double getNodeSep() {
		return nodesep;
	}

	public void setNodesep(double nodesep) {
		this.nodesep = nodesep;
	}

	public boolean isRecursive() {
		return isRecursive;
	}

	public void setRecursive(boolean isRecursive) {
		this.isRecursive = isRecursive;
	}

	public boolean isNoPublic() {
		return noPublic;
	}

	public void setNoPublic(boolean noPublic) {
		this.noPublic = noPublic;
	}

	public boolean isNoPrivate() {
		return noPrivate;
	}

	public void setNoPrivate(boolean noPrivate) {
		this.noPrivate = noPrivate;
	}

	public boolean isNoProtected() {
		return noProtected;
	}

	public void setNoProtected(boolean noProtected) {
		this.noProtected = noProtected;
	}
}

    
