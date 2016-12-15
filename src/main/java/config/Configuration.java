package config;

import generator.IGeneratorConfiguration;
import generator.IModifier;
import model.IModelConfiguration;
import runner.IRunnerConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

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
    private Collection<IModifier> filters;
    private boolean isRecursive;
    private String rankDir;

    public static Configuration getInstance() {
        Configuration conf = new Configuration();
        conf.setOutputFormat("png");
        conf.setFileName("out");
        conf.setNodesep(1);
        conf.setClasses(new ArrayList<>());
        conf.setFilters(new HashSet<>());
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

	public Collection<IModifier> getFilters() {
		return this.filters;
	}
	
	public void setFilters(Collection<IModifier> filters) {
		this.filters = filters;
	}

	public String getRankDir() {
		return rankDir;
	}

	public void setRankDir(String rankDir) {
		this.rankDir = rankDir;
	}
	
	@Override
	public String toString() {
		return    "Classes:                   " + classes + "\n"
				+ "Executable Path:           " + executablePath + "\n"
				+ "Output Extension:          " + outputFormat + "\n"
				+ "Output Directory:          " + outputDirectory + "\n"
				+ "Output file name:          " + fileName + "\n"
				+ "Node seperation value:     " + nodesep + "\n"
				+ "Filters:                   " + filters + "\n"
				+ "Recursive?:                " + isRecursive + "\n"
				+ "Rank Dir:                  " + rankDir;
	}
}

    
