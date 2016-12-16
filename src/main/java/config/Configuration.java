package config;

import java.util.ArrayList;

import generator.IGeneratorConfiguration;
import model.IModelConfiguration;
import runner.IRunnerConfiguration;
import utility.IFilter;
import utility.Modifier;

/**
 * Created by lamd on 12/7/2016. Edited by fineral on 12/13/2016
 */
public class Configuration implements IRunnerConfiguration, IGeneratorConfiguration, IModelConfiguration {

	private Iterable<String> classes;
	private String executablePath;
	private String outputFormat;
	private String outputDirectory;
	private String fileName;
	private double nodesep;
	private IFilter<Modifier> modifierFilter;
	private boolean isRecursive;
	private String rankDir;

	public static Configuration getInstance() {
		Configuration conf = new Configuration();
		conf.setOutputFormat("png");
		conf.setFileName("out");
		conf.setNodesep(1);
		conf.setClasses(new ArrayList<>());
		conf.setFilters(new IFilter<Modifier>() {
			@Override
			public boolean filter(Modifier data) {
				return true;
			}
		});
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

	public IFilter<Modifier> getModifierFilter() {
		return this.modifierFilter;
	}

	public void setFilters(IFilter<Modifier> filter) {
		this.modifierFilter = filter;
	}

	public String getRankDir() {
		return this.rankDir;
	}

	public void setRankDir(String rankDir) {
		this.rankDir = rankDir;
	}

	public String toString() {
		return "Classes:                   " + classes + "\n" + "Executable Path:           " + executablePath + "\n"
				+ "Output Extension:          " + outputFormat + "\n" + "Output file name:          " + fileName + "\n"
				+ "Output Directory:          " + outputDirectory + "\n" + "Node seperation value:     " + nodesep
				+ "\n" + "Filters:                   " + modifierFilter + "\n" + "Recursive?:                "
				+ isRecursive + "\n" + "Rank Dir:                  " + rankDir;
	}
}
