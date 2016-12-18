package config;

import generator.IGeneratorConfiguration;
import model.IModelConfiguration;
import runner.IRunnerConfiguration;
import utility.IFilter;
import utility.Modifier;

import java.util.ArrayList;

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
    private IFilter<Modifier> modifierFilter;
    private boolean isRecursive;
    private String rankDir;
    private String nodeStyle;
    private String parserKey;

    public static Configuration getInstance() {
        Configuration conf = new Configuration();
        conf.setOutputFormat("png");
        conf.setFileName("out");
        conf.setNodesep(1);
        conf.setClasses(new ArrayList<>());
        conf.setFilters(data -> true);
        conf.setNodeStyle("node [shape=record]");
        conf.setParseKey("default");
        return conf;
    }

    @Override
    public Iterable<String> getClasses() {
        return classes;
    }

	public void setClasses(Iterable<String> classes) {
		this.classes = classes;
	}

    @Override
    public String getExecutablePath() {
        return executablePath;
    }

	public void setExecutablePath(String executablePath) {
		this.executablePath = executablePath;
	}

    @Override
    public String getOutputFormat() {
        return outputFormat;
    }

	public void setOutputFormat(String outputExtension) {
		this.outputFormat = outputExtension;
	}

    @Override
    public String getOutputDirectory() {
        return outputDirectory;
    }

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

    @Override
    public String getFileName() {
        return fileName;
    }

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

    @Override
    public double getNodeSep() {
        return nodesep;
    }

	public void setNodesep(double nodesep) {
		this.nodesep = nodesep;
	}

    @Override
    public boolean isRecursive() {
        return isRecursive;
    }

	public void setRecursive(boolean isRecursive) {
		this.isRecursive = isRecursive;
	}

    @Override
    public IFilter<Modifier> getModifierFilters() {
        return this.modifierFilter;
    }

	public void setFilters(IFilter<Modifier> filter) {
		this.modifierFilter = filter;
	}

    @Override
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
    @Override
    public String getNodeStyle() {
        return this.nodeStyle;
    }

    @Override
    public String getParseKey() {
        return this.parserKey;
    }

    public void setParseKey(String parserKey) {
        this.parserKey = parserKey;
    }

    public void setNodeStyle(String nodeStyle) {
        this.nodeStyle = nodeStyle;
    }
}
