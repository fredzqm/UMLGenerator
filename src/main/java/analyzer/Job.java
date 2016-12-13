package analyzer;

import java.util.List;

import generator.IJob;

/**
 * Created by lamd on 12/7/2016.
 */
public class Job implements IJob{
	private final String name;
	private final List<String> classes;
	private final Format format;

	public Job(String name, List<String> classes, Format format) {
		super();
		this.name = name;
		this.classes = classes;
		this.format = format;
	}

	public String getName() {
		return name;
	}

	public List<String> getClasses() {
		return classes;
	}

	public Format getFormat() {
		return format;
	}

}
