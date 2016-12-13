package analyzer;

import generator.IJob;

import java.util.List;

/**
 * Created by lamd on 12/7/2016.
 */
public class Job implements IJob {
    private String name;
    private List<String> classes;
    private Format format;

    public Job(String name, List<String> classes) {
        this.name = name;
        this.classes = classes;
    }

    public Format getFormat() {
        return this.format;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getClasses() {
        return classes;
    }
}
